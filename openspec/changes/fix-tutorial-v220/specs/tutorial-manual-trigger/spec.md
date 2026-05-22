## MODIFIED Requirements

### Requirement: Manual tutorial trigger from Settings

The system SHALL provide a manual trigger for the tutorial from the Settings screen. When the user clicks the tutorial button on the Settings page, the tutorial SHALL start on the Record page with step 0 (welcome) displayed.

**Previous behavior:** `onRestartTutorial` attempted to navigate before showing the overlay, causing a race condition where the overlay appeared before navigation completed. Step 0 had `navigateTo = null`, so the overlay's LaunchedEffect did not navigate, and the welcome step appeared on the Settings page.

**New behavior:** `onRestartTutorial` SHALL only set `showTutorial = true` (no pre-navigation). Step 0 SHALL have `navigateTo = Screen.Record.route`. The overlay's `LaunchedEffect(currentStep)` SHALL handle all navigation, ensuring the overlay is fully composed before any navigation occurs.

#### Scenario: First manual trigger from Settings
- **WHEN** user is on Settings page and clicks the tutorial button
- **THEN** the tutorial overlay appears with step 0 (welcome) on the Record page
- **AND** the page has already switched to Record before the overlay becomes visible

#### Scenario: Repeated trigger from Settings
- **WHEN** user completes or skips the tutorial, then enters Settings and clicks the tutorial button again
- **THEN** overlay appears and functions identically to the first trigger

### Requirement: Step navigation with tab switching

The system SHALL navigate to the correct tab (Record, Report, or Settings) when the user clicks "上一步" or "下一步".

**Previous behavior:** Steps 1 (日历导航), 2 (添加记录), and 4 (图表数据点) had `navigateTo = null`, so LaunchedEffect did not navigate when entering these steps. Only steps 3, 5, and 6 switched tabs.

**New behavior:** Every step that references a specific page SHALL have a non-null `navigateTo` value. The `LaunchedEffect(currentStep)` SHALL trigger `onNavigate` for every step change.

#### Scenario: Step 1 (日历导航) tab navigation
- **WHEN** user clicks "下一步" from step 0
- **THEN** the overlay navigates to the Record page (step 1 references calendar navigation)

#### Scenario: Step 2 (添加记录) tab navigation
- **WHEN** user clicks "下一步" from step 1
- **THEN** the overlay stays on the Record page (step 2 is also about adding records)

#### Scenario: Step 4 (图表数据点) tab navigation
- **WHEN** user clicks "下一步" from step 3 (查看报表)
- **THEN** the overlay stays on the Report page (step 4 is about chart data points)

#### Scenario: Step 0 (欢迎) tab navigation
- **WHEN** user clicks "上一步" from step 1
- **THEN** the overlay stays on the Record page (step 0 has navigateTo = Record, but navigation to the same page is a no-op)

### Requirement: Step navigateTo mapping

The steps SHALL have these navigateTo values:

| Step | Title | navigateTo |
|------|-------|-----------|
| 0 | 欢迎使用体重记录 | `Screen.Record.route` |
| 1 | 日历导航 | `Screen.Record.route` |
| 2 | 添加记录 | `Screen.Record.route` |
| 3 | 查看报表 | `Screen.Report.route` |
| 4 | 图表数据点 | `Screen.Report.route` |
| 5 | 设置与自定义 | `Screen.Settings.route` |
| 6 | 删除记录 | `Screen.Record.route` |
| 7 | 教程完成 | `null` |
