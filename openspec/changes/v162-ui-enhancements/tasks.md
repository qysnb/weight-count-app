## 1. Period Chip Spacing

- [x] 1.1 `weight(...)` replaced with `padding(horizontal = 4.dp)`, parent Row uses `Arrangement.SpaceEvenly`.

## 2. Tag List — Remove Reorder Buttons

- [x] 2.1 Removed `onMoveUp`/`onMoveDown` params from `TagListSection`.
- [x] 2.2 Deleted up-arrow and down-arrow `IconButton` composables from each tag row.
- [x] 2.3 Removed `onMoveUp`/`onMoveDown` from `TagListSection` call site.
- [x] 2.4 Removed all `moveTagUp`/`moveTagDown` overloads and `swapTags` from `SettingsViewModel`.

## 3. About Page Version

- [x] 3.1 `"v1.6.1"` → `"v1.6.2"`.
