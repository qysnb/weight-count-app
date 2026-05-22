## MODIFIED Requirements

### Requirement: FAB positioned at bottom-center

The system SHALL render the floating action button (FAB) for adding weight records at the bottom-center of the Record screen, rather than bottom-right.

#### Scenario: FAB centered at bottom
- **WHEN** the Record screen renders
- **THEN** the FAB SHALL be horizontally centered at the bottom of the screen
- **THEN** the FAB SHALL have 16dp bottom padding
- **THEN** the FAB SHALL NOT overlap with any weight record items on the right side
