## Why

The v1.6.3 tag filter only allows single-tag selection, but users often want to combine multiple tags (e.g., "晨起" + "空腹") for a broader filtered view. The About page GitHub link has a redundant "GitHub:" prefix that should be removed, and the link should be centered.

## What Changes

- **Multi-select tag filter**: Change tag filter from single-select (`selectedTagId: Long?`) to multi-select toggle. Each tag chip toggles on/off when tapped. Tapping "全部" deselects all other tags. Records matching ANY of the selected tags are included in the chart.
- **About page GitHub link**: Remove "GitHub: " prefix from the link text, center the link text horizontally.

## Capabilities

### Modified Capabilities
- `data-reporting`: Tag filter supports multi-select toggle; "全部" resets selection
- `user-settings`: About page link shows URL only, centered

## Impact

- **Code changes only** — no new dependencies, no DB changes
- **Files affected**: `ReportViewModel.kt`, `ReportScreen.kt`, `AboutScreen.kt`
