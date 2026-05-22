## ADDED Requirements

### Requirement: Settings trigger starts tutorial correctly
The "使用教程" button in Settings SHALL immediately show the tutorial overlay without redundant navigation or data operations.

#### Scenario: Tap "使用教程" from Settings
- **WHEN** user taps "使用教程" in the Settings screen
- **THEN** the tutorial overlay SHALL appear immediately
- **THEN** the app SHALL navigate to the 记录 tab (handled by TutorialOverlay's `LaunchedEffect`)
- **THEN** `tutorialCompleted` DataStore key SHALL NOT be written to during trigger
