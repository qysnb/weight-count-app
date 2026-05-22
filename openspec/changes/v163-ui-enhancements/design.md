## Context

v1.6.2 has 3 preset tags ("饭前", "饭后", "睡前") seeded on first launch. The report page shows period-based line charts but cannot filter by tag — users who record weights at different times of day have no way to isolate specific contexts. About page shows v1.6.2 without a link to the GitHub repository.

## Goals / Non-Goals

**Goals:**
- Expand preset tags to 5: "空腹", "晨起", "饭前", "饭后", "睡前" (two new tags prepended)
- Add tag filtering to the report page, independent from date range selection
- Update About page to v1.6.3 with clickable GitHub link

**Non-Goals:**
- No DB schema migration — only seed data change (Room callback runs on `onCreate` only for new installs)
- No changes to existing tag rename/delete UI or logic
- No multi-select tag filtering (single tag selection at a time, or "all" mode)

## Decisions

### 1. Default tag expansion

**Approach**: Change `presets` list in `AppDatabase.SeedCallback.onCreate()` from `listOf("饭前", "饭后", "睡前")` to `listOf("空腹", "晨起", "饭前", "饭后", "睡前")`. New installs get 5 tags; existing users keep their existing tags (no migration needed).

### 2. Report page tag filter

**Approach**: Add a horizontal scrollable `LazyRow` of `FilterChip` components below the period chip row, displaying all user tags plus an "全部" (all) chip. When a tag chip is selected, the chart filter includes only records tagged with that tag. The "全部" chip resets to show all records.

**Data flow**:
- `ReportViewModel` exposes a `selectedTagId: Long?` (null = all records)
- `ReportViewModel` fetches tags via `tagRepository.getAllTags()`
- Weight DAO gets a new filtered query that joins tag IDs: `WHERE id IN (SELECT recordId FROM record_tag_cross_ref WHERE tagId = :tagId)`
- When `selectedTagId` changes, the ViewModel re-queries with the combined date + tag filter

**Why chip row over dialog**: Chips are immediately visible, zero-click access, consistent with the existing period chip pattern. A dialog adds an extra tap and hides available filters.

**Why single-select over multi-select**: Simpler UX for the common case (user typically wants one context at a time). Multi-select YAGNI for now.

### 3. About page GitHub link

**Approach**: Add a `TextButton` or `ClickableText` below the version label showing the GitHub URL. Use `UriHandler.openUri()` to open the browser.

## Risks / Trade-offs

| Risk | Mitigation |
|------|-----------|
| Tag filter query adds join overhead to chart rendering | Tag filter only applies when a non-null tag is selected; "全部" mode uses the existing unfiltered query |
| Existing users won't get new default tags | Room's `onCreate` only runs on fresh installs. New tag seeding for existing users is out of scope for this change |
| Tag filter may show no data for some combinations (e.g., "晨起" + last 7 days) | This is correct behavior — empty chart state already handled by existing chart code |
