## Why

Users need more granular control over the weight chart by filtering data through tags (e.g., view only "空腹" or "晨起" records), and the default tag set lacks "空腹" and "晨起" which are common measurement contexts. The About page still shows v1.6.2 and lacks a link to the GitHub repository.

## What Changes

- **New default tags**: Add "空腹" (fasting) and "晨起" (morning wake-up) as preset tags, inserted before the existing 3 defaults ("饭前", "饭后", "睡前"), making 5 total preset tags on first launch.
- **Tag filtering on report page**: Add a tag filter chip row or dialog on the report page that lets users select one or more tags. The chart data is filtered to include only weight records that have at least one of the selected tags. The tag filter is independent from the date range filter — users can choose any period (e.g., "7日") combined with any tag (e.g., "饭前").
- **About page**: Update version to v1.6.3 and add a clickable GitHub repository link (https://github.com/qysnb/weight-count-app).

## Capabilities

### Modified Capabilities
- `tag-management`: Default preset tags expanded from 3 to 5 (add "空腹", "晨起" before existing ones)
- `data-reporting`: Report page gains tag-based filtering independent from date range
- `user-settings`: About page shows v1.6.3 with GitHub link

## Impact

- **Database**: Room database version bump (new default tags seeded via callback, no schema migration needed)
- **Files affected**: `AppDatabase.kt`, `ReportScreen.kt`, `ReportViewModel.kt`, `AboutScreen.kt`, `WeightRepository.kt` or `ReportViewModel.kt` (for tag-filtered queries)
- **No breaking changes** to existing data model, DAO, or repository interfaces
