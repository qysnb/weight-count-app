## ADDED Requirements

### Requirement: Modify-record dialog has a delete button

The system SHALL display a red "删除" button at the left side of the bottom button row in the AddWeightDialog when it is used for editing an existing record.

#### Scenario: Delete button visible when editing
- **WHEN** user taps an existing weight record in the record list
- **THEN** the dialog SHALL show a red "删除" OutlinedButton to the left of the "取消" button
- **THEN** the bottom button row SHALL have delete on the left, cancel and confirm on the right

#### Scenario: Delete triggers confirmation dialog
- **WHEN** user taps the red "删除" button
- **THEN** the system SHALL show the existing delete confirmation AlertDialog ("删除记录" / "确定删除此条记录？")

#### Scenario: Delete button not visible when adding
- **WHEN** user taps the "记录" FAB to add a new record
- **THEN** the dialog SHALL NOT show the "删除" button
