## Context

v1.6.1 replaced `padding(horizontal = 6.dp)` with `Modifier.weight()` on period chips to make the yearly chip 10% wider. While the proportional goal was met, the weight-based approach eliminated all gaps between chips — the Row is filled edge-to-edge with no visual breathing room. Users report chips feel squeezed.

On the settings page, the tag reorder up/down arrows (added in v1.6.0, reworked in v1.6.1 with tag-ID lookup) still suffer from reliability issues on certain devices — Room Flow re-emission combined with rapid tapping causes missed or wrong-position moves. The arrows also add visual clutter to the already dense tag row UI.

## Goals / Non-Goals

**Goals:**
- Replace `Modifier.weight()` on period chips with `Arrangement.SpaceEvenly` — equal space allocated before, between, and after each chip; gaps scale with available width
- Remove the up/down IconButton from each tag row in `TagListSection`
- Remove unused `moveTagUp/Down` overloads from `SettingsViewModel`
- Update About page to v1.6.2

**Non-Goals:**
- No DB schema changes, no new tables or migrations
- No changes to tag creation, renaming, or deletion logic
- No changes to tag sorting model (sortOrder field kept for future use)

## Decisions

### 1. Period chip spacing

**Approach**: Remove `Modifier.weight()` from FilterChip, change parent Row to `Arrangement.SpaceEvenly`. Each chip uses its intrinsic width (based on label text), and the Row distributes remaining space equally before, between, and after chips.

**Why SpaceEvenly over SpaceBetween or spacedBy:**
- `SpaceBetween` has no space before first or after last chip — edges look flush against row bounds
- `spacedBy(fixedDp)` would need a fixed dp gap that doesn't scale with screen/button width
- `SpaceEvenly` allocates space dynamically — on a 360dp screen the gap might be ~4dp, on a 600dp tablet it could be ~20dp, naturally achieving the "~10% of button width" intent

### 2. Remove tag reorder buttons

**Approach**: Delete the two `IconButton` composables from each tag row in `TagListSection`. Remove the `onMoveUp` and `onMoveDown` callback parameters from `TagListSection` and its call site. Clean up unused `moveTagUp/Down` overloads from `SettingsViewModel`.

**Why remove instead of fix further**: Two iterations (v1.6 → v1.6.1) failed to make the arrows reliably responsive. The feature adds marginal value (users almost never reorder tags after initial setup) relative to the maintenance and UI clutter cost. Tag sort order remains in the data model via the `sortOrder` field for future drag-and-drop if needed, but the current button-based reorder is removed.

### 3. About version

**Approach**: Change `"v1.6.1"` → `"v1.6.2"` in `AboutScreen.kt`.

## Risks / Trade-offs

| Risk | Mitigation |
|------|-----------|
| `SpaceEvenly` may create uneven gaps if chips have very different intrinsic widths | All five period labels are short fixed text (2-4 Chinese chars); intrinsic widths are similar |
| Remove reorder buttons but users still want reordering | `sortOrder` field and Room migration remain intact — can add drag-and-drop or a dedicated reorder screen later |
| Unused `moveTagUp/Down` overloads may still be referenced | Clean them up in the same pass; build will catch any stray references |
