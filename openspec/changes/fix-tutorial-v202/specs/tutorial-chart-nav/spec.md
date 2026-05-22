## ADDED Requirements

### Requirement: Tutorial step for chart data point navigation
The tutorial SHALL include a step that guides the user through selecting data points on the chart and using the arrows above the chart to switch between data points.

#### Scenario: Step shows chart interaction
- **WHEN** the tutorial step for chart data point navigation is displayed
- **THEN** the step text SHALL describe how to tap a data point on the chart to select it and use the arrow buttons above the chart to switch between data points
- **THEN** the step SHALL be positioned after the "report introduction" step and before the "settings" step

#### Scenario: Step content correctness
- **WHEN** user views the chart data point step
- **THEN** description SHALL include: tapping a data point selects it and shows its details, arrow buttons (← →) switch to adjacent data points

### Requirement: Step order integration
The chart data point step SHALL be inserted after the report introduction step and before the settings step in the tutorial flow.

#### Scenario: Step sequence
- **WHEN** user progresses through the tutorial
- **THEN** step order SHALL be: welcome → calendar navigation → add record → report introduction → chart data point navigation → settings → completion
