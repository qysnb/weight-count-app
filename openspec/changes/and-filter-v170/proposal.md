## Why

The multi-select tag filter currently uses OR logic, showing records matching ANY selected tag. This is too loose for meaningful analysis — users who select "空腹" and "晨起" want records that are BOTH "空腹" AND "晨起" (AND logic). The About page also needs to reflect the new version v1.7.0.

## What Changes

1. **Report page tag filter**: Change the multi-select tag filter logic from OR (`any`) to AND (`all`). When two or more tags are selected, only records having ALL selected tags will be shown.
2. **About page version**: Update the version text from v1.6.3 to v1.7.0.

## Capabilities

### New Capabilities
*(none — both changes modify existing capabilities)*

### Modified Capabilities
- `data-reporting`: Tag filter requirement changes from OR to AND logic.
- `user-settings`: About page version string requirement changes.

## Impact

- `ReportViewModel.kt` — one-line change in `updateChart()` filter predicate
- `AboutScreen.kt` — version string change
