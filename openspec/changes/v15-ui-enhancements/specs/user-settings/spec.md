## ADDED Requirements

### Requirement: Tag entity has sortOrder field

The system SHALL store a `sortOrder` integer on each Tag to support user-customizable ordering.

#### Scenario: SortOrder persisted
- **WHEN** a new tag is created
- **THEN** its `sortOrder` SHALL be set to the current maximum sortOrder + 1 (so it appears last)
- **WHEN** tags are retrieved
- **THEN** they SHALL be ordered by `sortOrder ASC`

### Requirement: Tags can be reordered via drag handle

The system SHALL display a drag-handle icon on the left side of each tag row in the Settings tag list. Users SHALL be able to long-press the drag handle and drag the tag up or down to reorder it. The "删除" button SHALL shift slightly to accommodate the drag handle.

#### Scenario: Drag handle visible
- **WHEN** the tag list renders in Settings
- **THEN** each tag row SHALL display a drag-handle icon on the far left

#### Scenario: Long-press drag reorder
- **WHEN** user long-presses the drag-handle icon on a tag
- **THEN** the tag SHALL enter drag mode
- **WHEN** user drags the tag up or down
- **THEN** adjacent tags SHALL shift to make room
- **THEN** the reorder SHALL be persisted via Room

#### Scenario: New tag appears last
- **WHEN** user creates a new tag
- **THEN** the tag SHALL appear at the bottom of the list

## MODIFIED Requirements

### Requirement: About page displays correct version

The system SHALL display the current app version as v1.5 on the About page.

#### Scenario: View About page shows v1.5
- **WHEN** user taps "关于" in Settings
- **THEN** the version label SHALL display "v1.5"
