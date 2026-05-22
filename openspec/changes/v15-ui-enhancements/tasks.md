## 1. FAB Bottom-Center

- [x] 1.1 `Alignment.BottomEnd` → `Alignment.BottomCenter`.

## 2. Chart Date Range Header Font +15%

- [x] 2.1 `titleSmall` → `titleMedium`.

## 3. Period Chips Evenly Distributed

- [x] 3.1 `spacedBy(4.dp)` → `SpaceBetween`.
- [x] 3.2 Added `Modifier.weight(1f)` to each FilterChip.

## 4. Tag Reorder with Drag Handle

- [x] 4.1 Added `val sortOrder: Int = 0` to Tag entity.
- [x] 4.2 Query changed to `ORDER BY sortOrder ASC`; added `getMaxSortOrder()` query.
- [x] 4.3 `addTag` computes `maxSortOrder + 1`; overloaded `updateTag`.
- [x] 4.4 Version bumped to 2; `MIGRATION_1_2` with ALTER TABLE SQL; seed uses `sortOrder = index`.
- [x] 4.5 Added `moveTagUp(index)`, `moveTagDown(index)` swapping sortOrders.
- [x] 4.6 Tag list gets up/down arrow buttons + drag handle icons; delete button shifted left.
- [x] 4.7 `"v1.4"` → `"v1.5"`.
