## Why

The v1.4 build compiled successfully. Four remaining UX issues were identified: the FAB on the Record page sits at bottom-right, overlapping weight data on narrower screens; the chart date range header is still too small relative to navigation arrows and chart content; the period chips are left-aligned instead of evenly distributed across the row, looking unbalanced with 5 chips; and the tag list in Settings lacks drag-reorder capability, making it impossible for users to arrange tags in their preferred order. These changes polish the UX to v1.5 quality.

## What Changes

- **FAB moved to bottom-center**: Change the RecordScreen FAB position from `Alignment.BottomEnd` to `Alignment.BottomCenter`. This frees up the right edge for weight records and places the add action in a more thumb-friendly neutral position.

- **Chart date range header font enlarged further**: Increase from `titleSmall` (~14sp) to `titleMedium` (~16sp) — approximately +15% larger, matching the user's request for "比之前再大15%".

- **Period chips evenly distributed**: Replace `Arrangement.spacedBy(4.dp)` with `Arrangement.SpaceBetween` on the period chip Row, applying `Modifier.weight(1f)` to each FilterChip so the five chips (7日, 30日, 90日, 180日, 年度) stretch edge-to-edge.

- **Tag ordering with drag handle**: Add a `sortOrder` (Int) field to the Tag entity (default 0). Add a Room migration adding the column. Update the DAO query to order tags by `sortOrder ASC`. In the tag list UI, show a drag-handle icon on the left side of each tag row, with long-press drag-to-reorder via `detectDragGesturesAfterLongPress`. New tags default to the last position. The "删除" button moves slightly to the right. About page version becomes v1.5.

## Capabilities

### Modified Capabilities
- `weight-recording`: FAB moves from bottom-right to bottom-center
- `data-reporting`: Chart header font ~15% larger; period chips evenly distributed (SpaceBetween + weight)
- `user-settings`: Tag list gains drag-reorder with sortOrder; About version v1.5

## Impact

- **Code changes** — no new third-party dependencies (uses Compose Foundation's built-in gesture APIs)
- **Files affected**: `RecordScreen.kt`, `ReportScreen.kt`, `SettingsScreen.kt`, `SettingsViewModel.kt`, `Tag.kt`, `TagDao.kt`, `TagRepository.kt`, `AppDatabase.kt`, `AboutScreen.kt`
- **Database migration**: Room schema version 1→2 adds `sortOrder` column to `tags` table — requires a migration SQL
- **No breaking changes** to navigation, ViewModel interfaces, or record data model
