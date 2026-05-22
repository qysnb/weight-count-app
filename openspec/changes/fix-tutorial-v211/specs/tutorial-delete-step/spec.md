## ADDED Requirements

### Requirement: Tutorial step for deleting data
The tutorial SHALL include a step between the "设置与自定义" step and the final "教程完成" step that guides the user on how to delete an existing weight record.

#### Scenario: Step position
- **WHEN** user progresses through the tutorial
- **THEN** the delete-data step SHALL appear as the second-to-last step (between "设置与自定义" and "教程完成")
- **THEN** the step count SHALL update to reflect the new total number of steps

#### Scenario: Step content
- **WHEN** user reaches the delete-data step
- **THEN** the description SHALL explain how to delete a record: navigate to 记录 tab, tap a record, tap the delete button/icon
- **THEN** the step SHALL navigate the user to the 记录 tab
