## 1. Delete Button in Modify-Record Dialog

- [x] 1.1 Added `showDelete: Boolean = false, onDelete: () -> Unit = {}` params.
- [x] 1.2 Red OutlinedButton("删除") shown conditionally on left when showDelete is true.
- [x] 1.3 Row horizontalArrangement changed to `Arrangement.SpaceBetween`.
- [x] 1.4 EditWeightDialog passes `showDelete = true, onDelete = { showDeleteConfirm = true }`.

## 2. Chart Date Range Header Font Size

- [x] 2.1 `bodySmall` → `titleSmall` for dateRangeText style.

## 3. Period Selector: Remove Label, Add 180-Day Chip

- [x] 3.1 Added `CHART_180D_KEY` (int) and `CHART_180D_LABEL_KEY` (string) preference keys.
- [x] 3.2 Added `chart180dDays` (default 180) and `chart180dLabel` (default "180日") flows.
- [x] 3.3 Added `chart180dDays` → `"180d"` to `dayConfigs` combine.
- [x] 3.4 Added `chart180dLabel` → `"180d"` to `labelConfigs` combine.
- [x] 3.5 Added `"180d"` case to both `setChartPeriod` and `setChartPeriodLabel`.
- [x] 3.6 Added `ChartPeriod.SEMI_ANNUALLY("semiannually", "180日")` between QUARTERLY and YEARLY.
- [x] 3.7 Added `-> periodDaysMap["180d"] ?: 180` branch in `updateChartData`.
- [x] 3.8 Removed `Text("选择周期")` and `Spacer(weight)`. Added "180d" to PeriodCustomizer entries in SettingsScreen.

## 4. Settings: Remove "(预设)" Badge, Update About Version

- [x] 4.1 Removed `if (tag.isPreset) { Text("（预设）") }` block.
- [x] 4.2 `"v1.3"` → `"v1.4"`.
