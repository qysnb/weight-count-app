## ADDED Requirements

### Requirement: All recording UI uses Simplified Chinese

The system SHALL display all weight recording interface elements in Simplified Chinese.

#### Scenario: Dialog labels in Chinese
- **THEN** the FAB label SHALL read "记录"
- **THEN** the dialog title SHALL read "记录体重"
- **THEN** the weight input label SHALL read "体重（公斤）"
- **THEN** the confirm button SHALL read "确定"
- **THEN** the cancel button SHALL read "取消"
- **THEN** the delete button SHALL read "删除"
- **THEN** the confirmation message SHALL read "确定删除此条记录？"

### Requirement: User can log a new weight record

The system SHALL allow users to record their body weight with a timestamp and optional tags via a modal dialog.

#### Scenario: Successful weight entry via dialog
- **WHEN** user taps the "记录" FAB on the Record screen
- **THEN** an AddWeightDialog opens with DatePicker, TimePicker, weight numeric input, and tag selection chips
- **WHEN** user enters 70.5 kg, selects tags "饭前", sets date to 2026-05-21 08:30
- **THEN** user taps "确定" and the record is persisted to Room DB with correct timestamp
- **THEN** the dialog closes and the calendar/list updates automatically

#### Scenario: Validation — weight field is empty
- **WHEN** user opens AddWeightDialog and taps "确定" without entering weight
- **THEN** an inline error message "请输入体重" is shown below the weight field
- **THEN** the dialog does NOT close and no record is saved

#### Scenario: Validation — weight out of reasonable range
- **WHEN** user enters weight less than 20.0 or greater than 500.0
- **THEN** an inline validation error is shown
- **THEN** the record is NOT persisted

### Requirement: Add dialog has reduced transparency and two-line date layout

The system SHALL render the AddWeightDialog with sufficient opacity for readability, and display the date picker button as two lines (year on top, month+day below).

#### Scenario: Dialog background opacity
- **THEN** the dialog background overlay SHALL have opacity >= 0.6 (Material 3 default Dialog scrim)
- **THEN** all text inside the dialog SHALL be fully opaque (no alpha blending on text)

#### Scenario: Date/time button layout
- **WHEN** the AddWeightDialog is open
- **THEN** the date button SHALL display "2026年\n5月21日" across two lines (year \n month+day)
- **THEN** the time button SHALL have the same height as the date button
- **THEN** the time button SHALL display "08:30" on its own line

### Requirement: User can edit an existing weight record

The system SHALL allow users to modify weight, timestamp, or tags of any existing record by tapping on it in the calendar view or log list.

#### Scenario: Edit weight value
- **WHEN** user taps an existing record in the weight log list
- **THEN** an EditWeightDialog opens pre-filled with that record's current weight, tags, and time
- **WHEN** user changes weight from 70.5 to 71.0 and taps "确定"
- **THEN** the record is updated in Room DB
- **THEN** the chart and calendar reflect the new value

#### Scenario: Edit tags on a record
- **WHEN** user edits a record and deselects "饭前" and selects "饭后"
- **THEN** the RecordTagCrossRef table is updated accordingly
- **THEN** old associations are removed, new ones inserted

### Requirement: User can delete a weight record

The system SHALL allow users to delete a weight record with a confirmation step.

#### Scenario: Delete record from edit dialog
- **WHEN** user opens the edit dialog and taps "删除"
- **THEN** a confirmation dialog "确定删除此条记录？" is shown
- **WHEN** user taps "确定"
- **THEN** the record and its tag associations are deleted from Room DB
- **THEN** the UI and chart update immediately via Flow emission

#### Scenario: Cancel deletion
- **WHEN** user taps "取消" on the confirmation dialog
- **THEN** the deletion is aborted and the record remains unchanged

### Requirement: Calendar has month/year selector

The system SHALL provide a month/year switcher above the calendar grid, allowing the user to navigate between different months and years instead of being fixed to the current month.

#### Scenario: Month/year selector visible
- **THEN** the calendar header SHALL display a row with "<" (previous month), current "yyyy年M月" label, and ">" (next month) buttons
- **THEN** tapping "<" SHALL decrement the displayed month by 1
- **THEN** tapping ">" SHALL increment the displayed month by 1
- **THEN** the month/year label SHALL be tappable to open a year picker (optional enhancement)

#### Scenario: Calendar navigates to different month
- **WHEN** user taps ">" on May 2026
- **THEN** the calendar updates to show June 2026
- **THEN** weight badges for June 2026 days are loaded and displayed

#### Scenario: Day with one record shows weight badge
- **WHEN** there is exactly one weight record on 2026-05-21
- **THEN** the calendar cell for 2026-05-21 shows "70.5" in a small badge

#### Scenario: Day with multiple records shows average
- **WHEN** there are 3 weight records on 2026-05-21
- **THEN** the calendar cell shows the average weight rounded to 1 decimal

#### Scenario: Day with no records shows empty
- **WHEN** there are no records on 2026-05-20
- **THEN** the calendar cell shows no badge
