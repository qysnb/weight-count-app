## ADDED Requirements

### Requirement: About page displays GitHub link

The About page SHALL display a clickable link to the GitHub repository (https://github.com/qysnb/weight-count-app) below the version label.

#### Scenario: View About page shows GitHub link
- **WHEN** user taps "关于" in Settings
- **THEN** the version label SHALL display "v1.6.3"
- **THEN** a clickable link "GitHub" or the full URL SHALL appear below the version
- **WHEN** user taps the link
- **THEN** the system SHALL open the URL in the default browser

## REMOVED Requirements

### Requirement: About page displays version v1.6.2

**Reason**: Version text updated from v1.6.2 to v1.6.3. Previous requirement for v1.6.2 is superseded.

**Migration**: N/A — version text update is a single string change.
