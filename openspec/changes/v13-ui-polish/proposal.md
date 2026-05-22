## Why

The v1.2 build revealed UI issues in the AddWeightDialog (time button height is excessive), report screen chart navigation is limited (no way to browse adjacent data points), date range button text wraps awkwardly, and the About page version is outdated. These changes polish the UX to v1.3 quality.

## What Changes

- **AddWeightDialog time button height fix**: The time button in the dialog is overly tall because the Column+Spacer approach pushed it to match the date button's two-line height, but without proper constraints. Fix by using `Modifier.height(IntrinsicSize.Min)` on the parent Row, and wrapping time text in a centered Column without Spacers.
- **AddWeightDialog width**: Increase from 65% to 85% of screen width.
- **Chart tooltip navigation**: Add left/right arrow buttons at the top of the chart area for switching between adjacent data points. Show date range (e.g., "2026.6.1 - 2026.6.20") centered at the top.
- **Date range button width**: Widen the two date range buttons so "起始日期"/"结束日期" fit on one line without wrapping. Left-align start button with "7日" above, right-align end button with "年度" above. Remove date text from buttons after selection (buttons always show "起始日期"/"结束日期"), show the actual date range at the chart top center instead.
- **About page version**: v1.2 → v1.3.

## Capabilities

### Modified Capabilities
- `weight-recording`: AddWeightDialog width 85%, time button height fixed via IntrinsicSize
- `data-reporting`: Chart tooltip gets prev/next arrow navigation; date range buttons widened and realigned; date display moved to chart header
- `user-settings`: About page version becomes v1.3

## Impact

- **Code changes only** — no new dependencies, no schema changes, no DB migrations
- **Files affected**: `AddWeightDialog.kt`, `ReportScreen.kt`, `ReportViewModel.kt`, `AboutScreen.kt`
- **No breaking changes** to data model or API surfaces
