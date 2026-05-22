## ADDED Requirements

### Requirement: System provides default preset tags

The system SHALL seed the database with 3 preset tags on first launch: "饭前", "饭后", "睡前". Preset tags SHALL be deletable but re-creatable by the user.

#### Scenario: First launch seeds default tags
- **WHEN** the app launches for the first time
- **THEN** the Room callback inserts 3 default tags with isPreset = true
- **THEN** these tags appear in the tag selection dialog

#### Scenario: Preset tag deletion
- **WHEN** user deletes a preset tag in Settings
- **THEN** it is removed from the Tag table
- **THEN** existing records that used this tag lose the association (cascade delete)

### Requirement: User can create a custom tag

The system SHALL allow users to create new tags with a name of 1–20 Chinese/English characters. The Tag editing UI SHALL use Simplified Chinese for all prompts, error messages, and labels.

#### Scenario: Tag editor UI in Chinese
- **THEN** the add button label SHALL read "添加"
- **THEN** the input prompt SHALL read "输入标签名称"
- **THEN** the duplicate error SHALL read "标签已存在"
- **THEN** the delete confirmation SHALL read "删除标签「<name>」？"

#### Scenario: Create custom tag via Settings
- **WHEN** user navigates to Settings → 自定义标签 → 添加
- **THEN** an input dialog appears with a text field
- **WHEN** user enters "运动后" and taps "确定"
- **THEN** a new Tag is persisted with isPreset = false
- **THEN** "运动后" appears in all tag selection chip lists

#### Scenario: Duplicate tag name rejected
- **WHEN** user tries to create a tag with a name that already exists
- **THEN** an error "标签已存在" is shown
- **THEN** no duplicate tag is created

### Requirement: User can rename a custom tag

The system SHALL allow users to rename any non-preset tag.

#### Scenario: Rename custom tag
- **WHEN** user long-presses a custom tag in Settings
- **THEN** a rename dialog appears with the current name pre-filled
- **WHEN** user changes "运动后" to "运动前" and taps "确定"
- **THEN** the tag name is updated in the Tag table
- **THEN** all existing records with that tag reflect the new name

### Requirement: User can delete a custom tag

The system SHALL allow users to delete a custom tag. Preset tags SHALL be deletable as well (with cascade).

#### Scenario: Delete custom tag with confirmation
- **WHEN** user swipes a custom tag left in the Settings list
- **THEN** a confirmation "删除标签「运动后」？" is shown
- **WHEN** user confirms
- **THEN** the tag is deleted
- **THEN** all RecordTagCrossRef rows with this tagId are cascaded
