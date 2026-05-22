## ADDED Requirements

### Requirement: Tutorial step for calendar navigation
The tutorial SHALL include a step that guides the user through using the four arrow buttons in the record page calendar header to switch year and month.

#### Scenario: Step shows calendar arrows
- **WHEN** the tutorial step for calendar navigation is displayed
- **THEN** the step text SHALL describe the four arrow buttons (← previous month, → next month, ← previous year, → next year) in the record page calendar
- **THEN** the step SHALL be positioned after the welcome step and before the "add record" step

#### Scenario: Step content correctness
- **WHEN** user views the calendar navigation step
- **THEN** description SHALL include the location (top of record page calendar area) and function of each arrow

### Requirement: Step order integration
The calendar navigation step SHALL be inserted between the welcome step and the add-record step in the tutorial flow.

#### Scenario: Step sequence
- **WHEN** user progresses through the tutorial
- **THEN** step order SHALL be: welcome → calendar navigation → add record → report → settings → completion
