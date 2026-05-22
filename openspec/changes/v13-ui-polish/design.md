## Context

v1.2 compiled successfully but revealed UI issues: AddWeightDialog time button is excessively tall (Spacer(weight) in Column inside OutlinedButton), chart lacks point-to-point navigation, date range buttons wrap text, and About page is outdated.

## Goals / Non-Goals

**Goals:**
- AddWeightDialog width 85%, time button height normalized via `IntrinsicSize.Min` on parent Row
- Chart tooltip gets prev/next arrow buttons at top-left/right; date range text centered at top
- Date range buttons always show "起始日期"/"结束日期", widened to avoid text wrap, aligned with period chips above
- About page version v1.3

**Non-Goals:**
- No new dependencies, no DB changes, no navigation graph changes

## Decisions

1. **Time button height fix**: Remove the `Spacer(weight(1f))` from time button Column. Set `Modifier.height(IntrinsicSize.Min)` on the parent Row so both date and time buttons shrink to their smallest natural height. The date button's two-line Column defines the Row height naturally.

2. **Chart nav arrows**: Add a `Row` wrapper above the Canvas inside the chart Box area with:
   - Left `IconButton(Icons.AutoMirrored.Filled.ArrowBack)` for previous data point
   - Center `Text` showing date range "yyyy.M.d - yyyy.M.d" when chart data exists
   - Right `IconButton(Icons.AutoMirrored.Filled.ArrowForward)` for next data point
   - `tooltipIndex` state promoted from `WeightChart` internal to `ReportScreen` level, passed down

3. **Date range buttons static text**: Remove `startStr`/`endStr` dynamic text; always hardcode "起始日期" and "结束日期". Widen buttons: use `Modifier.weight(0.35f)` for each button with `Modifier.fillMaxWidth()` on the Row. Remove `Text("选择范围")` label entirely since date range is now shown at chart top.

4. **Layout alignment**: Period chips row has `Text("选择周期")` + `Spacer(weight)` + chips. The date range row will have just the two buttons with matching left/right padding, aligned to the period chip positions.

## Risks / Trade-offs

| Risk | Mitigation |
|------|-----------|
| `IntrinsicSize.Min` may cause layout pass perf issue on older compose versions | Only applies to a 2-item Row, negligible impact |
| Nav arrows at chart top may overlap with chart content | Position arrows in a separate Row above the Canvas, not inside it |
