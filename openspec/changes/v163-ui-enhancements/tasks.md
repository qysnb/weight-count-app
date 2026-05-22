## 1. Default Tags Expansion

- [x] 1.1 Presets expanded to `listOf("空腹", "晨起", "饭前", "饭后", "睡前")`.

## 2. Tag Filtered Query

- [x] 2.1 Added `getRecordsInRangeWithTag(startTime, endTime, tagId)` in `WeightDao.kt` with subquery on `record_tag_cross_ref`.
- [x] 2.2 Added delegating method in `WeightRepository.kt`.

## 3. Report Page Tag Filter UI + ViewModel

- [x] 3.1 Added `tagRepo`, `allTags`, `selectedTagId: Long?` to `ReportViewModel`. Unified `updateChart()` handles date + tag filtering independently.
- [x] 3.2 Added horizontal scrollable tag filter chip row in `ReportScreen.kt` with "全部" + user tags.

## 4. About Page

- [x] 4.1 Version `"v1.6.2"` → `"v1.6.3"`, added clickable GitHub link via `LocalUriHandler`.
