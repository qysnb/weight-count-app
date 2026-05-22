## MODIFIED Requirements

### Requirement: Tag list hides "(预设)" badge

The system SHALL NOT display "(预设)" text next to any tag name in the custom tags section, regardless of whether the tag is marked as a preset.

#### Scenario: No preset badge visible
- **WHEN** user views the custom tags section in Settings
- **THEN** no tag name SHALL have "(预设)" text appended

### Requirement: About page displays correct version

The system SHALL display the current app version as v1.4 on the About page.

#### Scenario: View About page shows v1.4
- **WHEN** user taps "关于" in Settings
- **THEN** the version label SHALL display "v1.4"
