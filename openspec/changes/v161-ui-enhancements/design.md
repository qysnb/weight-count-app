## Context

v1.6 added `padding(horizontal = 6.dp)` to period chips for breathing room, but this uniform padding gives no proportional sizing — "年度" looks the same width as "7日". The tag reorder buttons were fixed by moving `.clickable` from the parent Row to the tag name Text, but users report they still don't respond. Investigation reveals the root cause: `moveTagUp/Down` take a list index captured by `forEachIndexed` at composition time; if Room Flow re-emits between render and user tap (e.g., after any database write), the index becomes stale.

## Goals / Non-Goals

**Goals:**
- Replace uniform chip padding with `Modifier.weight()` — yearly chip gets 1.1x weight, others 1.0x
- Switch tag move operations from index-based to tag-ID-based
- Update About page to v1.6.1

**Non-Goals:**
- No DB schema changes, no new tables or migrations
- No changes to tag creation, renaming, or deletion logic

## Decisions

### 1. Period chip proportional widths

**Approach**: Replace `Modifier.padding(horizontal = 6.dp)` with conditional weight:
- `YEARLY → Modifier.weight(1.1f)`
- Others → `Modifier.weight(1.0f)`
Keep `Arrangement.SpaceBetween` on the parent Row. FilterChip labels already center their text content by default.

With 5 chips (4 × 1.0 + 1 × 1.1 = 5.1 total), "年度" gets 1.1/5.1 ≈ 21.6% of row width vs others at 1.0/5.1 ≈ 19.6% — a ~10% relative increase.

### 2. Tag reorder by tag ID

**Approach**: 
- Add `moveTagUp(tagId: Long)` and `moveTagDown(tagId: Long)` to SettingsViewModel
- Internally, find the tag's current position in `_uiState.value.tags` by ID, then perform the sortOrder swap with the adjacent tag
- Retain the old `moveTagUp/Down(index: Int)` overloads for backward compatibility (or remove if no other callers)
- In `TagListSection`, pass `tag.id` instead of `index` to the move handlers
- Use `Log.d` to output the tag ID and resulting sortOrders for debugging

This eliminates the stale-index race condition because the lookup happens at execution time (inside the coroutine), not at composition time.

### 3. About version

**Approach**: Change `"v1.6"` → `"v1.6.1"` in `AboutScreen.kt`.

## Risks / Trade-offs

| Risk | Mitigation |
|------|-----------|
| Weight-based chips may be too thin for long custom labels | Intrinsic minimum width of FilterChip prevents overflow; text truncation is acceptable for very long labels |
| Tag ID lookup has O(n) cost per move | List size is capped at ~20 tags at most — negligible performance impact |
