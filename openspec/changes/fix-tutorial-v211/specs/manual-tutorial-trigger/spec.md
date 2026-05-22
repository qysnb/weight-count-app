## ADDED Requirements

### Requirement: Tutorial only accessible from Settings
The application SHALL NOT auto-trigger the tutorial on first launch. The tutorial SHALL only be accessible via the "使用教程" button in the Settings screen.

#### Scenario: New user first launch
- **WHEN** a new user opens the app for the first time
- **THEN** the tutorial overlay SHALL NOT appear automatically
- **THEN** the user SHALL see the app in normal operation mode

#### Scenario: Starting tutorial from Settings
- **WHEN** user taps "使用教程" in the Settings screen
- **THEN** the tutorial overlay SHALL appear and the app SHALL navigate to the 记录 tab

### Requirement: Settings button renamed
The Settings screen button SHALL display "使用教程" instead of "重新体验教程".

#### Scenario: Button text
- **WHEN** user opens the Settings screen
- **THEN** the tutorial entry button SHALL display text "使用教程"
