## MODIFIED Requirements

### Requirement: System provides default preset tags

The system SHALL seed the database with 5 preset tags on first launch: "空腹", "晨起", "饭前", "饭后", "睡前", in that order. Preset tags SHALL be deletable but re-creatable by the user.

#### Scenario: First launch seeds 5 default tags
- **WHEN** the app launches for the first time
- **THEN** the Room callback inserts 5 default tags with isPreset = true
- **THEN** the tags appear in the order: 空腹, 晨起, 饭前, 饭后, 睡前
- **THEN** all 5 tags appear in the tag selection dialog and tag filter

#### Scenario: Preset tag deletion
- **WHEN** user deletes a preset tag in Settings
- **THEN** it is removed from the Tag table
- **THEN** existing records that used this tag lose the association (cascade delete)
