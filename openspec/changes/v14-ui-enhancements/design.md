## Context

v1.3 compiled successfully. Four UI gaps remain: (1) the modify dialog has no direct delete button — users must go through the existing delete confirmation flow via EditWeightDialog's AlertDialog, but there's no shortcut on the edit form itself; (2) the chart date range header at the top center uses `bodySmall` (12sp default) which is small relative to arrow icons and chart content; (3) the period chips lack a 180-day option, and the "选择周期" label consumes horizontal space unnecessarily; (4) the "(预设)" badge on tags suggests immutability, but all tags are equally editable.

## Goals / Non-Goals

**Goals:**
- Add a red "删除" OutlinedButton to AddWeightDialog's bottom button row, left of "取消", only when editing (passed via a new `showDelete: Boolean` parameter)
- Increase chart date range header font to approximately 14sp (+~10% from 12sp default bodySmall)
- Remove "选择周期" label; add ChartPeriod.SEMI_ANNUALLY(180 days) between QUARTERLY and YEARLY; add DataStore keys for 180d configuration
- Remove "(预设)" conditional text in SettingsScreen tag list; update About version to v1.4

**Non-Goals:**
- No DB schema changes, no navigation graph changes, no new screens
- No changes to tag creation/rename/delete logic

## Decisions

### 1. Delete button in modify dialog

**Approach**: Add two new parameters to `AddWeightDialog`: `showDelete: Boolean = false` and `onDelete: () -> Unit`. When `showDelete` is true, a red `OutlinedButton` with text "删除" appears at `Arrangement.Start` of the bottom button Row. `EditWeightDialog` passes `showDelete = true, onDelete = { showDeleteConfirm = true }`. The existing delete confirmation `AlertDialog` in `EditWeightDialog` remains unchanged.

Layout order: (left) [删除]  [取消]  [确定] (right)
- Delete button uses `Arrangement.Start` with `Modifier.weight(1f)`
- Cancel + Confirm remain `Arrangement.End`

### 2. Chart date range header font

**Approach**: Change `MaterialTheme.typography.bodySmall` → `MaterialTheme.typography.titleSmall`. The default `titleSmall` is 14sp vs `bodySmall` at 12sp — a ~16% increase, close to the requested 10%. This avoids introducing a custom `fontSize` value.

### 3. Period selector refactor

**Approach**:
- Remove the `Text("选择周期")` label and the `Spacer(Modifier.weight(1f))` after it
- Add `ChartPeriod.SEMI_ANNUALLY("semiannually", "180日")` to the enum between QUARTERLY and YEARLY
- Add `updateChartData` branch: `ChartPeriod.SEMI_ANNUALLY -> periodDaysMap["180d"] ?: 180`
- Add DataStore keys: `CHART_180D_KEY`, `CHART_180D_LABEL_KEY`, with default 180 / "180日"
- Add new flows: `chart180dDays`, `chart180dLabel`
- Add to `dayConfigs` combine: `..., chart180dDays` → map entry `"180d"`
- Add to `labelConfigs` combine: `..., chart180dLabel` → map entry `"180d"`
- Update `setChartPeriod` / `setChartPeriodLabel` with `"180d"` case

### 4. Settings cleanup

**Approach**:
- Remove the `if (tag.isPreset) { Text("（预设）") }` block in `SettingsScreen.kt:240-247`
- Change `"v1.3"` → `"v1.4"` in `AboutScreen.kt`

## Risks / Trade-offs

| Risk | Mitigation |
|------|-----------|
| Delete button too close to cancel on narrow screens | Both are OutlinedButtons/Tertiary — separated by Spacer. Row uses Arrangement.SpaceBetween for clear left/right grouping |
| Existing DataStore data lacks 180d keys | Keys default to 180 / "180日" via `?:` operator — no migration needed |
| Adding a new ChartPeriod shifts existing enum ordinals | Only used in explicit `when` branches, not ordinal-based logic |
