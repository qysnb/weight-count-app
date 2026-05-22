## MODIFIED Requirements

### Requirement: Tag reorder buttons work correctly

The system SHALL ensure the up/down arrow buttons on each tag row in the Settings tag list respond to taps and trigger the reorder logic. The `.clickable` for renaming a tag SHALL be scoped to the tag name text only, not the entire row.

#### Scenario: Up arrow tap moves tag up
- **WHEN** user taps the up arrow button on a tag row
- **THEN** the tag SHALL move up one position in the list
- **THEN** the tag's sortOrder SHALL be swapped with the adjacent tag above

#### Scenario: Down arrow tap moves tag down
- **WHEN** user taps the down arrow button on a tag row
- **THEN** the tag SHALL move down one position
- **THEN** the tag's sortOrder SHALL be swapped with the adjacent tag below

#### Scenario: Tapping tag name triggers rename
- **WHEN** user taps the tag name text
- **THEN** the rename dialog SHALL open

### Requirement: About page displays correct version

The system SHALL display the current app version as v1.6 on the About page.

#### Scenario: View About page shows v1.6
- **WHEN** user taps "关于" in Settings
- **THEN** the version label SHALL display "v1.6"
