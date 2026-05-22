## 1. Period Chip Proportional Widths

- [x] 1.1 `padding(horizontal = 6.dp)` replaced with `weight(if (YEARLY) 1.1f else 1.0f)`.

## 2. Tag Reorder by Tag ID

- [x] 2.1 Added `moveTagUp(tagId: Long)` and `moveTagDown(tagId: Long)` overloads that look up by ID.
- [x] 2.2 `onMoveUp/Down(index)` changed to `onMoveUp/Down(tag.id)`.
- [x] 2.3 `TagListSection` callback types changed from `(Int) -> Unit` to `(Long) -> Unit`.

## 3. About Page Version

- [x] 3.1 `"v1.6"` → `"v1.6.1"`.
