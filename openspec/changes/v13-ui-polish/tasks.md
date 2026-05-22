## 1. AddWeightDialog Width and Button Height Fix

- [x] 1.1 Surface `fillMaxWidth(0.65f)` → `fillMaxWidth(0.85f)`.
- [x] 1.2 Removed `Spacer(weight)` from time button Column.
- [x] 1.3 Added `Modifier.height(IntrinsicSize.Min)` to date/time Row.
- [x] 1.4 Time button simplified to single Text (no Column wrapper needed).

## 2. Chart Navigation Arrows and Date Range Header

- [x] 2.1 `selectedPointIndex` state moved to `ReportScreen` level.
- [x] 2.2 Added `selectedPointIndex: Int, onPointIndexChange: (Int) -> Unit, dateRangeText: String` params.
- [x] 2.3 Internal `tooltipIndex` replaced with `selectedPointIndex`.
- [x] 2.4 Nav Row added above Canvas with left arrow, center dateRangeText, right arrow.
- [x] 2.5 Left arrow wraps: `(curr - 1 + size) % size`.
- [x] 2.6 Right arrow wraps: `(curr + 1) % size`.
- [x] 2.7 Date range formatted in ReportScreen, passed to WeightChart.
- [x] 2.8 `mutableStateOf` used (no extra import needed).

## 3. Date Range Buttons Static Text + Width

- [x] 3.1 Buttons always show "起始日期"/"结束日期".
- [x] 3.2 `Text("选择范围")` removed.
- [x] 3.3 Buttons use `weight(0.35f)` with `Spacer(weight(0.1f))` between.

## 4. About Page Version

- [x] 4.1 `"v1.2"` → `"v1.3"`.
