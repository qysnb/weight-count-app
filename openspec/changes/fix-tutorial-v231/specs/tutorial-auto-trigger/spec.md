## ADDED Requirements

### Requirement: First-launch auto-trigger

The system SHALL automatically show the tutorial on the user's first launch. Detection SHALL use the `tutorialCompleted` flag in DataStore.

#### Scenario: New user (first launch)
- **WHEN** the app launches for the first time
- **THEN** `tutorialCompleted` is `false` (default)
- **AND** a `LaunchedEffect` sets `showTutorial = true`
- **AND** the tutorial overlay appears on the Record page (start destination) with step 0

#### Scenario: Returning user (tutorial completed)
- **WHEN** the user has previously completed the tutorial and relaunches the app
- **THEN** `tutorialCompleted` is `true`
- **AND** no auto-trigger occurs

#### Scenario: DataStore hasn't loaded yet
- **WHEN** the app launches but DataStore hasn't returned a value yet
- **THEN** `collectAsState(initial = true)` returns `true` (preventing premature trigger)
- **AND** once DataStore loads and confirms `tutorialCompleted == false`, the auto-trigger fires
