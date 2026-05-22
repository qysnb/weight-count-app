## 1. Calendar Year Navigation

- [x] 1.1 In `RecordViewModel.kt`, add `navigateYear(delta: Int)` function: read `displayYear` from state, add delta, write back via `copy(displayYear = displayYear + delta)`. `displayMonth` unchanged.
- [x] 1.2 In `CalendarHeader.kt`, update `MonthYearSelector` signature: add `onPreviousYear: () -> Unit` and `onNextYear: () -> Unit` parameters.
- [x] 1.3 In `MonthYearSelector`, add two `IconButton`s for year navigation: `[<<]` (KeyboardArrowLeft) and `[>>]` (KeyboardArrowRight) around the existing month row. Layout: `Row { yearBack; monthBack; Text(label, weight=1f); monthFwd; yearFwd }`.
- [x] 1.4 In `RecordScreen.kt`, wire `MonthYearSelector` new params: `onPreviousYear = { viewModel.navigateYear(-1) }`, `onNextYear = { viewModel.navigateYear(1) }`.

## 2. AddWeightDialog Constrain Width + Button Height Matching

- [x] 2.1 In `AddWeightDialog.kt`, constrain dialog content width: wrap in `Surface(Modifier.fillMaxWidth(0.65f), RoundedCornerShape(16.dp))`.
- [x] 2.2 Wrap the dialog content in a `Surface` with `RoundedCornerShape(16.dp)` and `tonalElevation = 6.dp`.
- [x] 2.3 Make time button height match date button height: wrap the time `OutlinedButton` content in a `Column(Arrangement.Center)` with `Spacer(weight=1f)` above/below.

## 3. Chart Tooltip Bottom Margin

- [x] 3.1 In `ReportScreen.kt` → `WeightChart` → tooltip height: `lineHeight * totalLines` → `lineHeight * (totalLines + 0.5f)`.

## 4. Evenly-Distributed X-Axis Labels

- [x] 4.1 Replaced dedup logic with `timeMin`/`timeMax`, label count (5/6/8 based on span), evenly-spaced timestamps.
- [x] 4.2 X position: `leftPadding + ((t - timeMin) / (timeMax - timeMin)) * chartWidth`.
- [x] 4.3 Old `seenDays` / `labelIndices` / `displayIndices` block removed entirely.

## 5. Date Range Selector Row + Boundary Fix

- [x] 5.1 Merged "选择范围" + two buttons into one `Row` with `Spacer(weight=1f)`.
- [x] 5.2 `setCustomRangeStart` normalizes to 00:00:00.000 via `Calendar`.
- [x] 5.3 `setCustomRangeEnd` normalizes to 23:59:59.999 via `Calendar`.
- [x] 5.4 `updateChartWithRange` requires both `start` and `end` non-null before filtering.

## 6. About Page Version and Developer

- [x] 6.1 Changed version from `"v1.0"` to `"v1.2"`.
- [x] 6.2 Changed developer from `"开发者: WeightCount Team"` to `"开发者: Qyforest"`.
