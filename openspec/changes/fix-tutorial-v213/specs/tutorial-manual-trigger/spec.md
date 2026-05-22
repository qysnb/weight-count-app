## ADDED Requirements

### Requirement: Settings trigger navigates before showing tutorial
The "使用教程" button SHALL first navigate to the Record tab, then show the tutorial overlay. This ensures the target tab is rendered before the overlay appears.

#### Scenario: Tap "使用教程" from Settings
- **WHEN** user taps "使用教程" in the Settings screen
- **THEN** the app SHALL navigate to the Record tab FIRST
- **THEN** the tutorial overlay SHALL appear AFTER navigation is initiated
- **THEN** the welcome step SHALL NOT attempt to navigate again

### Requirement: Step 0 has no navigation
The welcome tutorial step SHALL have `navigateTo = null` since navigation is handled by the trigger callback.

#### Scenario: Step 0 shown
- **WHEN** the tutorial starts
- **THEN** the welcome step SHALL display without triggering any `onNavigate` call
