## Why

The v1.5 build compiled successfully but two issues were reported: the period chips on the Report page are packed edge-to-edge with no breathing room between them, making the row look cramped; and the tag reorder up/down arrow buttons on the Settings page do not respond to taps because the parent Row's `clickable` modifier intercepts the touch events before they reach the IconButtons. These are regression fixes from v1.5 changes.

## What Changes

- **Period chip spacing**: Replace `Arrangement.SpaceBetween` + `Modifier.weight(1f)` with `Arrangement.SpaceEvenly` or add `Modifier.padding(horizontal = )` to each chip to create gaps roughly 15% of chip width between chips. Text remains centered within each chip.

- **Tag reorder button fix**: Move the `.clickable { onRenameClick(tag) }` modifier from the parent `Row` to only the tag name `Text` composable, so the up/down IconButton onClick events are no longer consumed by the parent's clickable gesture detector.

- **About page version**: v1.5 → v1.6.

## Capabilities

### Modified Capabilities
- `data-reporting`: Period chips have proportional gaps between them with centered text
- `user-settings`: Tag reorder up/down buttons now functional; About version v1.6

## Impact

- **Code changes only** — no new dependencies, no DB changes, no migrations
- **Files affected**: `ReportScreen.kt`, `SettingsScreen.kt`, `AboutScreen.kt`
- **No breaking changes** to data model, ViewModel interfaces, or API surfaces
