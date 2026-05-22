## Why

The v1.3 build compiled successfully and addressed chart navigation, dialog sizing, and date range button alignment. User feedback and testing revealed four remaining UX gaps: the modify-record dialog lacks a delete shortcut (forcing users through a separate confirmation flow); the chart date range header is too small relative to surrounding elements; the period selector lacks a 180-day (semi-annual) option, which is a natural intermediate interval between quarterly and yearly; and the custom tags section still shows a "(预设)" badge that misleads users into thinking presets are unmodifiable. These changes polish the UX to v1.4 quality.

## What Changes

- **Modify-record dialog delete button**: Add a red "删除" button to the left of "取消" in the AddWeightDialog button row when invoked from EditWeightDialog. The button triggers the existing delete confirmation flow directly — no new confirmation dialog needed at the AddWeightDialog level.

- **Chart date range header font size**: Increase the `dateRangeText` font style from `bodySmall` to a scaled-up size — approximately 10% larger. Use `titleSmall` or a manual `fontSize` bump (e.g., from 12sp default bodySmall to ~14sp) to achieve the visual increase.

- **Period selector: remove label, add 180-day chip**: Remove the "选择周期" text label from the period chip row. Add a new `ChartPeriod.SEMI_ANNUALLY` enum entry ("180日") between `QUARTERLY` and `YEARLY`. Add corresponding DataStore keys (`chart_180d`, `chart_180d_label`) for user-customizable days/label in settings. Add the "180d" key to `allPeriodConfigs` day/label maps.

- **Settings: remove preset badge, update About version**: Remove the `if (tag.isPreset)` block that displays "(预设)" next to tag names in the custom tags section. Change About page version from v1.3 to v1.4.

## Capabilities

### Modified Capabilities
- `weight-recording`: Modify-record dialog gets a red delete button alongside cancel
- `data-reporting`: Chart date range header enlarged ~10%; period selector adds 180-day chip, removes label text
- `user-settings`: "(预设)" badge removed from tags; About version becomes v1.4

## Impact

- **Code changes** — no new dependencies, no DB migrations
- **Files affected**: `AddWeightDialog.kt`, `EditWeightDialog.kt`, `ReportScreen.kt`, `ReportViewModel.kt`, `SettingsDataStore.kt`, `SettingsScreen.kt`, `SettingsViewModel.kt`, `AboutScreen.kt`
- **DataStore additions**: Two new preference keys (`chart_180d`, `chart_180d_label`) — backward compatible, no migration needed
- **No breaking changes** to data model or API surfaces
