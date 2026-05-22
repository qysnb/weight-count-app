## ADDED Requirements

### Requirement: About page displays version number

The About page SHALL display the current app version string.

#### Scenario: View About page shows version
- **WHEN** user taps "关于" in Settings
- **THEN** the version text "v1.7.0" SHALL appear below the app name

## MODIFIED Requirements

### Requirement: About page displays GitHub link

The About page SHALL display a clickable link to the GitHub repository (https://github.com/qysnb/weight-count-app) centered horizontally, with only the URL as link text.

#### Scenario: View About page shows centered GitHub link
- **WHEN** user taps "关于" in Settings
- **THEN** the link text SHALL display "https://github.com/qysnb/weight-count-app" (without "GitHub:" prefix)
- **THEN** the link SHALL be centered horizontally
- **WHEN** user taps the link
- **THEN** the system SHALL open the URL in the default browser
