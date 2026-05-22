## Context

This iteration targets UI/UX polish on the v1.1 Android weight tracker app (Kotlin + Jetpack Compose + Material 3). The app already has working screens for recording, reporting, and settings. The changes refine existing composables — no new screens, no data model changes, no dependency additions. All changes are in the Compose UI layer.

## Goals / Non-Goals

**Goals:**
- Add year navigation buttons (previous/next year) to the calendar header on the Record screen
- Redesign AddWeightDialog as a proper Dialog (~65% screen width) with matched date/time button heights and rounded corners
- Increase chart tooltip bottom margin by 0.5 line-height to prevent crowding
- Replace per-datapoint x-axis labels with evenly-distributed labels across the selected time range (earliest, latest, N intermediate)
- Merge "选择范围" label and two date picker buttons into the same Row; fix date range to cover full start/end days
- Update About page version to v1.2 and developer to "Qyforest"

**Non-Goals:**
- No DB schema changes, no migrations
- No new screens or navigation routes
- No dependency additions or removals
- No data model changes (Room entities, DAOs, repositories untouched)
- No new capabilities — only modifying existing ones

## Decisions

1. **Year buttons alongside month buttons in same Row**
   - `MonthYearSelector` adds two `IconButton`s for year navigation (`<<` / `>>`) alongside existing month navigation (`<` / `>`)
   - Layout: `[<<] [<] [yyyy年M月] [>] [>>]`
   - `RecordViewModel` gets `onPreviousYear()` / `onNextYear()` functions that adjust `displayYear` by ±1
   - Year change preserves `displayMonth` (same month, different year)

2. **AddWeightDialog → full Dialog with proportional sizing**
   - Change from `Dialog(onDismissRequest)` with inner `Column` → same structure but width constrained to ~65% of screen
   - Use `Modifier.fillMaxWidth(0.65f)` on the inner Column
   - The two-line date button (year \n month+day) already exists; ensure time button wraps in a Column with same intrinsic height via `Modifier.height(IntrinsicSize.Min)` on parent Row
   - Round corners via `MaterialTheme.shapes.extraLarge` on the Column's `Surface` shape parameter
   - Layout stays: WeightInput → Row(DateBtn + TimeBtn) → Tags → Buttons

3. **Tooltip bottom margin +0.5 line-height**
   - In `WeightChart`, `tooltipHeight` calculation adds 0.5 extra `lineHeight` units to bottom padding
   - Current: `tooltipHeight = 8.dp.toPx() + lineHeight * totalLines`
   - New: `tooltipHeight = 8.dp.toPx() + lineHeight * (totalLines + 0.5)`

4. **Evenly-distributed x-axis labels**
   - Remove per-datapoint dedup logic; replace with time-range-based distribution
   - Calculate: `timeMin` (earliest data point) and `timeMax` (latest data point)
   - Generate N time points evenly spaced between `timeMin` and `timeMax`
   - Use `SimpleDateFormat("M/d")` to format each label
   - N = 5 for 7-day, N = 6 for 30-day, N = 8 for 90-day/year views (clamped to data point count)

5. **Date range label+buttons in same row**
   - Move "选择范围" label inline with the two date buttons: `Row { Text("选择范围") ; Spacer ; OutlinedButton(start) ; OutlinedButton(end) }`
   - Date range logic: `rangeStart` set to start-of-day (00:00:00.000), `rangeEnd` set to end-of-day (23:59:59.999)
   - In `ReportViewModel.setCustomRangeStart/End`, normalize the millis to day boundaries

6. **About page: version + developer string change**
   - Find the About composable (inline in Navigation or separate screen)
   - Change version string from whatever it is to `"v1.2"`
   - Change developer string from whatever it is to `"Qyforest"`

## Risks / Trade-offs

| Risk | Mitigation |
|------|-----------|
| Year navigation may overflow display bounds on narrow screens | `MonthYearSelector` uses `Arrangement.SpaceBetween` with 5 icon buttons; text label shrinks via `Modifier.weight(1f)` |
| Dialog at 65% width may look narrow on tablets | Use `fillMaxWidth(0.65f)` with `maxWidth = 400.dp` cap |
| Evenly-spaced x-axis labels may not align with actual data points | This is intentional — labels represent time divisions, not data points (avoids overlap) |
| Date range normalization (00:00 / 23:59) could miss data at exact boundaries | This is correct — `00:00` includes 00:00:00, `23:59:59.999` includes the entire last day |
