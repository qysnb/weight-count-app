## ADDED Requirements

### Requirement: Always-composed overlay

The `TutorialOverlay` composable SHALL always be present in the composition tree. Visibility SHALL be controlled via a `visible: Boolean` parameter. The AnimatedVisibility inside the overlay SHALL show/hide content in response to this parameter.

#### Scenario: Initial app launch
- **WHEN** the app launches for the first time
- **THEN** the TutorialOverlay is present in the composition tree (but not visible)
- **AND** `visible` is `false` until `showTutorial` becomes `true`

#### Scenario: Manual trigger from Settings
- **WHEN** user clicks the tutorial button in Settings
- **THEN** `showTutorial` becomes `true`
- **AND** the TutorialOverlay's `visible` becomes `true`
- **AND** the overlay content appears via AnimatedVisibility enter animation

### Requirement: State reset on visibility change

When `visible` changes from `false` to `true`, the overlay SHALL reset `currentStep` to 0 and `isCollapsed` to `false`.

#### Scenario: Restart tutorial after completing
- **WHEN** user completes the tutorial (showTutorial = false), then later clicks the tutorial button again
- **THEN** the overlay resets to step 0
- **AND** the overlay is not collapsed

#### Scenario: Auto-trigger on first launch
- **WHEN** DataStore loads and `tutorialCompleted` is `false`
- **THEN** `showTutorial` becomes `true`
- **AND** the overlay appears with step 0 (welcome)
