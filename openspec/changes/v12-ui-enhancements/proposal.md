## Why

The v1.1 build is functionally complete but has UI/UX issues reported during testing: calendar navigation is limited to month-level only, the AddWeightDialog has overlapping text, chart tooltip and x-axis labels are cramped, date range selection logic is incomplete, and the About page version/developer info is outdated. These changes polish the user experience to v1.2 quality.

## What Changes

- **Calendar year navigation**: Add previous/next year buttons alongside existing month buttons in the calendar header
- **AddWeightDialog redesign**: Replace current layout with a full-page-width Dialog (60-70% of page), rounded corners, two-line date button (year + month/day) with height matched to time button
- **Chart tooltip bottom margin**: Add 0.5 line-height extra padding below tooltip text to prevent crowding
- **X-axis label distribution**: Switch from per-data-point labels to evenly distributed labels across the time range (earliest, latest, and N intermediate points), eliminating date overlap
- **Date range selector reflow**: Merge "选择范围" label + two date buttons into same row; fix date range logic (start date = 00:00, end date = 23:59)
- **About page update**: Version → v1.2, Developer → "Qyforest"

## Capabilities

### Modified Capabilities
- `weight-recording`: Calendar header gains year navigation; AddWeightDialog uses full-page Dialog layout with matched date/time button heights
- `data-reporting`: X-axis labels distribute evenly across time range (not per-datapoint); tooltip gets extra bottom margin; date range selector moves to same row as label; date range logic fixes start/end boundaries
- `user-settings`: About page version becomes v1.2 and developer becomes "Qyforest"

## Impact

- **Code changes only** — no new dependencies, no schema changes, no DB migrations
- **Files affected**: `CalendarHeader.kt`, `AddWeightDialog.kt`, `ReportScreen.kt`, `ReportViewModel.kt`, `AboutScreen.kt`
- **No breaking changes** to data model or API surfaces
- **No spec-level behavior changes** — all existing requirements remain; only UI/UX refinements
