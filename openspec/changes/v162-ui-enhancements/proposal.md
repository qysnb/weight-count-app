## Why

The v1.6.1 builds compiled successfully, but the report page period chips are still visually cramped — the weight-based approach fills the row with no gap between chips, and text just barely fits. On the settings page, the tag reorder up/down arrow buttons proved unreliable and distract from the primary name/rename/delete workflow. About page still shows v1.6.1.

## What Changes

- **Period chip spacing**: Remove `Modifier.weight()` from period filter chips. Use `Arrangement.SpaceEvenly` on the parent Row so each chip gets equal breathing room around it, with space dynamically sized based on available width. Chip label text remains centered.

- **Remove tag reorder buttons**: Delete the `IconButton(onClick = { onMoveUp(tag.id) })` and `IconButton(onClick = { onMoveDown(tag.id) })` from each tag row in `TagListSection`. Keep the tag name text, rename button, and delete button.

- **About page version**: v1.6.1 → v1.6.2.

## Capabilities

### Modified Capabilities
- `data-reporting`: Period chips use `Arrangement.SpaceEvenly` with intrinsic width; gaps proportional to available width
- `user-settings`: Remove tag reorder buttons from custom tag rows; About version v1.6.2

## Impact

- **Code changes only** — no new dependencies, no DB changes, no migrations
- **Files affected**: `ReportScreen.kt`, `SettingsScreen.kt`, `AboutScreen.kt` (SettingsViewModel unchanged but its `moveTagUp/Down(tagId: Long)` becomes unused)
- **No breaking changes** to data model, DAO, or repository interfaces
