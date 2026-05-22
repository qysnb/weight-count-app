## ADDED Requirements

### Requirement: All Settings UI uses Simplified Chinese

The system SHALL display every Settings screen label, section header, and dialog in Simplified Chinese.

#### Scenario: Settings menu items in Chinese
- **THEN** the three settings options display as "自定义标签", "报表自定义", "关于"

#### Scenario: About page in Chinese
- **THEN** the about section header SHALL read "关于"
- **THEN** the licenses link SHALL read "开源许可"
- **THEN** developer info SHALL be in Chinese where applicable

### Requirement: User can manage custom tags in Settings

The Settings screen SHALL provide a "自定义标签" section to view, add, rename, and delete tags.

#### Scenario: Tag list displays all tags
- **WHEN** user opens Settings → 自定义标签
- **THEN** a list of all tags is displayed with preset tags visually distinguished (e.g., icon or label)

#### Scenario: Add tag from Settings
- **WHEN** user taps the "+" button in the tag list header
- **THEN** an AddTagDialog opens with a text input
- **WHEN** user enters a valid name and confirms
- **THEN** the tag is created and appears in the list

#### Scenario: Rename tag from Settings
- **WHEN** user long-presses a tag item
- **THEN** a rename dialog appears
- **WHEN** user enters a new name and confirms
- **THEN** the tag is updated

#### Scenario: Delete tag from Settings
- **WHEN** user swipes left on a tag item
- **THEN** a delete confirmation appears
- **WHEN** user confirms
- **THEN** the tag and all its associations are removed

### Requirement: User can customize report period names and values

The system SHALL allow users to modify both the numeric values AND the display names of the report preset periods in Settings.

#### Scenario: Customize period value
- **WHEN** user opens Settings → 报表自定义
- **THEN** a list of current presets is shown with both name and day count
- **WHEN** user taps a period entry
- **THEN** a dialog appears with two fields: "名称" (name) and "天数" (day count)
- **WHEN** user changes "7日" → "一周" and 7 → 5
- **THEN** the Report screen shows the button labeled "一周" which queries 5-day data

#### Scenario: Period list displays current config
- **THEN** each period entry SHALL display as "7日 (7天)" showing both name and day count
- **THEN** tapping the entry opens the edit dialog

### Requirement: About page displays app info

The Settings screen SHALL include an "关于" section with developer info and open-source licenses.

#### Scenario: View About page
- **WHEN** user taps "关于" in Settings
- **THEN** a screen appears showing:
  - App name and version
  - Developer information
  - "开源许可" link that opens a standard LicenseActivity or dialog
