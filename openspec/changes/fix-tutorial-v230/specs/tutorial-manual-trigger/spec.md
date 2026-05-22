## MODIFIED Requirements

### Requirement: Manual tutorial trigger — no initial navigation

The system SHALL NOT auto-navigate when the tutorial is triggered. The overlay SHALL appear on the current page (Settings). Auto-navigation SHALL only occur when the user clicks "下一步" or "上一步" within the tutorial.

**Previous behavior:** Step 0 had `navigateTo = Screen.Record.route`, causing `LaunchedEffect(0)` to call `onNavigate(Record)` during the overlay's initial composition. This navigation's `popUpTo(start) + restoreState = true` disrupted the overlay, making it disappear.

**New behavior:** Step 0 has `navigateTo = null`. LaunchedEffect(0) does nothing. The overlay appears and remains visible. User clicks "下一步" to navigate to Record (step 1), which uses the same `onNavigate` callback — but at this point the overlay is fully established and survives the navigation.

#### Scenario: First trigger from Settings
- **WHEN** user is on Settings page and clicks the tutorial button
- **THEN** the tutorial overlay appears on the Settings page
- **AND** step 0 (welcome) is displayed
- **AND** no navigation occurs automatically

#### Scenario: Click "下一步" from step 0
- **WHEN** user clicks "下一步" on step 0
- **THEN** the overlay navigates to the Record page
- **AND** step 1 (日历导航) is displayed
- **AND** the overlay remains visible throughout

#### Scenario: Second trigger from Settings
- **WHEN** user completes the tutorial and triggers it again from Settings
- **THEN** behavior is identical to the first trigger — overlay appears on the current page
