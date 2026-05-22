## Context

v1.4 compiled successfully. The FAB at bottom-right overlaps records on the record page. The chart date range header at `titleSmall` (~14sp) still looks small against the arrow icons. The five period chips are packed left instead of filling the row. Tags cannot be reordered — users want to arrange them by usage frequency or category priority.

## Goals / Non-Goals

**Goals:**
- Move FAB from `Alignment.BottomEnd` to `Alignment.BottomCenter` in RecordScreen
- Change chart header `titleSmall` → `titleMedium` (~14sp→~16sp, ~15% increase)
- Change period chip Row `Arrangement.spacedBy(4.dp)` → `Arrangement.SpaceBetween` with `Modifier.weight(1f)` on each chip
- Add `sortOrder` field to Tag entity; Room v1→v2 migration; DAO sorts by `sortOrder ASC`
- Add drag-handle icon + long-press drag reorder to tag list; new tags appended last; About v1.5

**Non-Goals:**
- No third-party library additions (uses Compose Foundation gestures)
- No changes to record data model, navigation, or report chart logic
- No changes to AddWeightDialog or EditWeightDialog

## Decisions

### 1. FAB bottom-center

**Approach**: Change `Modifier.align(Alignment.BottomEnd)` → `Modifier.align(Alignment.BottomCenter)` in RecordScreen.kt line 88. Keep the 16.dp bottom padding. This is a one-line change.

### 2. Chart header font +15%

**Approach**: Change `MaterialTheme.typography.titleSmall` → `MaterialTheme.typography.titleMedium` for the `dateRangeText` Text composable. `titleMedium` defaults to ~16sp vs `titleSmall` at ~14sp — a ~14% increase, close to the requested 15%.

### 3. Period chips evenly distributed

**Approach**: Replace `Arrangement.spacedBy(4.dp)` with `Arrangement.SpaceBetween`. Apply `Modifier.weight(1f)` to each `FilterChip`. This stretches all 5 chips (7日, 30日, 90日, 180日, 年度) evenly across the full row width.

### 4. Tag reorder via drag handle

**Approach**:
- **Entity**: Add `val sortOrder: Int = 0` to the Tag data class. No change to existing constructors needed due to the default value.
- **Migration**: Add `ALTER TABLE tags ADD COLUMN sortOrder INTEGER NOT NULL DEFAULT 0` to a Room migration from version 1→2. Update `AppDatabase.version`.
- **DAO**: Change `ORDER BY isPreset DESC, name ASC` to `ORDER BY sortOrder ASC`.
- **Repository**: In `addTag()`, query max sortOrder and set new tag's sortOrder to max+1. Add `updateTag(tag: Tag)` method for reorder updates.
- **SettingsViewModel**: Add `moveTagUp(index: Int)` / `moveTagDown(index: Int)` functions that swap sortOrders between adjacent tags.
- **Tag list UI**: In `TagListSection`, use a `LazyColumn` (instead of `Column` with `forEach`) for drag support. Each row: drag handle icon (left), tag name (center), delete button (right). Drag handle uses `Modifier.pointerInput` with `detectDragGesturesAfterLongPress` to trigger reorder, using `mutableStateListOf` for local reorder state.
- **New tag default**: `addTag` computes `max(sortOrder) + 1` so new tags always appear last.

The "删除" button is moved slightly left by adding a small spacer between the tag name and delete button, creating room for the drag handle on the far left.

Layout: `[drag handle] [tag name] [Spacer(weight)] [删除]`

## Risks / Trade-offs

| Risk | Mitigation |
|------|-----------|
| Drag gesture may interfere with rename click | Drag handle has explicit `clickable` disabled, only responds to long-press drag |
| Room migration may fail on downgrade | Bump version only, no downgrade path needed for alpha |
| LazyColumn + drag reconstitution may flicker | Use `animateItemPlacement` modifier on each item for smooth transitions |
| Weight-based chips may be too thin for long labels | Minimum chip intrinsic width + label padding prevents overflow |
