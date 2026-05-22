## MODIFIED Requirements

### Requirement: Tag reorder uses tag ID instead of list index

The system SHALL use stable tag IDs for reorder operations instead of list indices, ensuring the move action works reliably even when Room Flow re-emits new tag data between the button render and user tap.

#### Scenario: Up arrow moves tag up by ID
- **WHEN** user taps the up arrow button on a tag row
- **THEN** the ViewModel SHALL look up the tag by its ID in the current UI state
- **THEN** the tag SHALL swap sortOrders with the adjacent tag above

#### Scenario: Down arrow moves tag down by ID
- **WHEN** user taps the down arrow button on a tag row
- **THEN** the ViewModel SHALL look up the tag by its ID in the current UI state
- **THEN** the tag SHALL swap sortOrders with the adjacent tag below

### Requirement: About page displays correct version

The system SHALL display the current app version as v1.6.1 on the About page.

#### Scenario: View About page shows v1.6.1
- **WHEN** user taps "关于" in Settings
- **THEN** the version label SHALL display "v1.6.1"
