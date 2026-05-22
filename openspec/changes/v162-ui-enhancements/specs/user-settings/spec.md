## REMOVED Requirements

### Requirement: Tag reorder uses tag ID instead of list index

**Reason**: Tag reorder up/down buttons removed from the tag list UI. The arrow buttons proved unreliable across device configurations after two fix iterations. Reordering adds marginal value relative to visual clutter and maintenance cost.

**Migration**: No action needed by users. Tag `sortOrder` field retained in the data model for future drag-and-drop or dedicated reorder screen.

### Requirement: About page displays correct version

**Reason**: Version text updated from v1.6.1 to v1.6.2. Previous requirement for v1.6.1 is superseded.

**Migration**: N/A — version text update is a single string change.

## ADDED Requirements

### Requirement: Tag list rows show name, rename, and delete only

The system SHALL display each custom tag row with three elements: tag name label, rename icon button, and delete icon button. The up-arrow and down-arrow icon buttons SHALL NOT be rendered.

#### Scenario: Tag row has no reorder arrows
- **WHEN** the settings screen displays the custom tags list
- **THEN** each tag row SHALL show the tag name label
- **THEN** each tag row SHALL show the rename icon button
- **THEN** each tag row SHALL show the delete icon button
- **THEN** each tag row SHALL NOT show any up-arrow or down-arrow icon button

### Requirement: About page displays version v1.6.2

The system SHALL display the current app version as v1.6.2 on the About page.

#### Scenario: View About page shows v1.6.2
- **WHEN** user taps "关于" in Settings
- **THEN** the version label SHALL display "v1.6.2"
