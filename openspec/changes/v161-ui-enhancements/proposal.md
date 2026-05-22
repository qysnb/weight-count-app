## Why

The v1.6 build compiled successfully but two issues remain: the period chips use uniform padding so "年度" has no visual distinction from other chips, making it feel smaller than the other period labels; and the tag reorder up/down arrow buttons still do not respond to taps — the index-based move approach is fragile because Room Flow re-emissions can shift indices between render and tap, causing the move to fail silently or target the wrong tag.

## What Changes

- **Period chip proportional widths**: Replace uniform `padding(horizontal = 6.dp)` with `Modifier.weight()` — `weight(1.1f)` for the "年度" chip (10% wider than baseline), `weight(1.0f)` for the other four chips. Text remains centered within each chip.

- **Tag reorder by tag ID**: Change `moveTagUp(index: Int)` / `moveTagDown(index: Int)` to accept tag ID instead of list index. The ViewModel looks up the tag by ID in the current state and finds its actual index at execution time, avoiding race conditions with Room Flow re-emissions.

- **About page version**: v1.6 → v1.6.1.

## Capabilities

### Modified Capabilities
- `data-reporting`: Period chips use proportional weight distribution; "年度" is 10% wider
- `user-settings`: Tag reorder buttons use tag-ID-based invocation (reliable); About version v1.6.1

## Impact

- **Code changes only** — no new dependencies, no DB changes, no migrations
- **Files affected**: `ReportScreen.kt`, `SettingsScreen.kt`, `SettingsViewModel.kt`, `AboutScreen.kt`
- **No breaking changes** to data model, DAO, or repository interfaces
