## Context

v1.6.3 introduced tag filtering on the report page using a single-select model (`selectedTagId: Long?`). Users tapping a tag chip select it exclusively; tapping another tag replaces the selection. This is limiting for users who record weights under multiple conditions (e.g., both "晨起" and "空腹") and want to see all those records combined.

The About page GitHub link currently prefixes the URL with "GitHub: ", which is redundant since the link itself is descriptive.

## Goals / Non-Goals

**Goals:**
- Change tag filter to multi-select toggle — each chip toggles its inclusion in the filter set
- Records matching ANY selected tag appear in chart (OR logic)
- Tapping "全部" clears all tag selections (shows all records)
- Remove "GitHub: " prefix from About page link, center the link text

**Non-Goals:**
- No changes to tag creation, renaming, or deletion
- No changes to period chips or date range selector
- No changes to database schema or queries

## Decisions

### 1. Multi-select tag filter

**Approach**: Replace `selectedTagId: Long?` with `selectedTagIds: Set<Long>` in `ReportUiState`. UI logic:
- Tapping a tag chip: if its ID is in the set → remove it; if not → add it
- Tapping "全部": clear the set (`emptySet()`)
- Filter logic: if set is empty → show all records; otherwise → show records where at least one of their tag IDs is in the set

**ViewModel changes:**
- `selectTag(tagId: Long?)` → `toggleTag(tagId: Long)` and `selectAllTags()`
- Remove the old `selectedTagId` state, replace with `selectedTagIds: Set<Long>`
- Update `updateChart()` to filter by set membership

**Why toggle over checkbox row**: Matches the existing chip interaction pattern. Users already understand chip taps from period selection.

**Why OR logic over AND**: Users typically want to see "all records for these contexts" not "records that happen to have all these tags simultaneously" (which is rare).

### 2. About page link cleanup

**Approach**: Change text from `"GitHub: https://github.com/qysnb/weight-count-app"` to `"https://github.com/qysnb/weight-count-app"`. Add `Modifier.fillMaxWidth()` and `TextAlign.Center` to center it.

## Risks / Trade-offs

| Risk | Mitigation |
|------|-----------|
| Users may be confused by toggle behavior initially | Chip selected state provides clear visual feedback; "全部" chip offers a clear reset path |
| Multi-select with many tags may produce wide chip rows | Chip row is already horizontally scrollable — no layout issues |
