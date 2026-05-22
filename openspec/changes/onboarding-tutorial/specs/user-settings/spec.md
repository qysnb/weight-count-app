## MODIFIED Requirements

### Requirement: About page displays version number

The About page SHALL display the current app version string.

#### Scenario: View About page shows version
- **WHEN** user taps "关于" in Settings
- **THEN** the version text "v2.0.0" SHALL appear below the app name

### Requirement: About page displays developer credit

The About page SHALL display the developer credit text.

#### Scenario: View About page shows developer
- **WHEN** user taps "关于" in Settings
- **THEN** the developer text "开发者: Qysnb with DeepSeek V4 Flash" SHALL appear below the description

### Requirement: About page displays GitHub link

The About page SHALL display a clickable link to the GitHub repository (https://github.com/qysnb/weight-count-app) centered horizontally, with only the URL as link text.

#### Scenario: View About page shows centered GitHub link
- **WHEN** user taps "关于" in Settings
- **THEN** the link text SHALL display "https://github.com/qysnb/weight-count-app" (without "GitHub:" prefix)
- **THEN** the link SHALL be centered horizontally
- **WHEN** user taps the link
- **THEN** the system SHALL open the URL in the default browser
