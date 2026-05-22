## Context

The app currently has a multi-select tag filter on the Report page using OR logic (`any`). Users expecting AND logic will see incorrect results when selecting multiple tags. The About page displays version v1.6.3, which needs updating to v1.7.0 for this release.

## Goals / Non-Goals

**Goals:**
- Change tag filter predicate from `any` to `all` in `ReportViewModel.updateChart()`
- Update version string in `AboutScreen.kt` from "v1.6.3" to "v1.7.0"

**Non-Goals:**
- No UI layout changes
- No data model changes
- No new screens or components

## Decisions

- **One-line change in ViewModel**: The filter lambda on line 157 changes from `rec.tags.any { it.id in selectedTagIds }` to `selectedTagIds.all { id -> rec.tags.any { it.id == id } }`. No state changes needed — the `selectedTagIds: Set<Long>` and toggle mechanism stays identical.
- **Direct string edit in AboutScreen**: Version is a hardcoded string on line 65 — change "v1.6.3" to "v1.7.0".

## Risks / Trade-offs

- AND filtering with many selected tags may return empty charts more often — but this is the expected behavior users want.
- Version string is duplicated in `build.gradle.kts` (`versionName`) and the About screen. If this gap grows, consider a shared constant. For now, manual sync is acceptable for a single change.
