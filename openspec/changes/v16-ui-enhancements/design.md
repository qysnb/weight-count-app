## Context

v1.5 introduced period chips with `Arrangement.SpaceBetween` + `Modifier.weight(1f)`, which stretches chips across the full row width with zero gaps between them. The tag list added up/down reorder arrow buttons inside a `Row` that has `.clickable { onRenameClick(tag) }` — the parent clickable consumes all tap events, so IconButton onClick handlers never fire.

## Goals / Non-Goals

**Goals:**
- Add gaps between period chips (~15% of chip width), keep edge-to-edge alignment, center text
- Fix tag reorder buttons by moving the rename click handler from the Row to the tag name Text only
- Update About page version to v1.6

**Non-Goals:**
- No DB changes, no migrations, no new dependencies
- No changes to models, ViewModels, or repositories

## Decisions

### 1. Period chip spacing

**Approach**: Remove `Modifier.weight(1f)` from each FilterChip. Apply `Modifier.padding(horizontal = 6.dp)` to each chip to add breathing room. Use `Arrangement.SpaceBetween` on the parent Row so chips naturally distribute edge-to-edge at their intrinsic content widths. Localized `androidx.compose.ui.text.style.TextAlign.Center` on the chip label text ensures centering within each chip.

On a typical phone (360dp width), 5 chips at ~60dp intrinsic width each = 300dp total, leaving ~60dp for gaps. 4 gaps of ~15dp each = ~15% of a ~60dp chip width (9dp), close to the target. The padding value is approximate — the visual result of "not too cramped" is more important than an exact 15% calculation.

### 2. Tag reorder button fix

**Approach**: Remove the `.clickable { onRenameClick(tag) }` modifier from the parent `Row`. Instead, apply `.clickable { onRenameClick(tag) }` directly to the tag name `Text` composable. The up/down IconButton onClick handlers will now receive touch events properly since they are no longer shadowed by a parent clickable.

### 3. About version

**Approach**: Change `"v1.5"` → `"v1.6"` in `AboutScreen.kt`.

## Risks / Trade-offs

| Risk | Mitigation |
|------|-----------|
| Period chip padding value may not look proportional on all screen sizes | Padding is in dp (fixed), but chips use intrinsic widths so distribution adapts naturally; testing on reference devices ensures it looks acceptable |
| Removing Row clickable reduces tap target for rename | Tag name Text remains clickable with adequate padding; entire row area remains tappable for delete and move buttons |
