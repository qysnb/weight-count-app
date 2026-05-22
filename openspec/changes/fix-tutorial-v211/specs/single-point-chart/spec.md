## ADDED Requirements

### Requirement: Chart renders single data point
The weight chart SHALL correctly render when only one data point is available, drawing a single dot at the corresponding position on the canvas.

#### Scenario: Single data point rendering
- **WHEN** the chart has exactly one data point
- **THEN** the chart SHALL render the grid lines and axis labels
- **THEN** the chart SHALL render a single data point circle at the center of the chart area
- **THEN** the chart SHALL NOT draw a line (no line between points)
- **THEN** the chart SHALL render the x-axis date label for the single point
- **THEN** the chart SHALL support selecting the single point (tooltip display)

#### Scenario: Tap detection with single point
- **WHEN** the chart has exactly one data point and user taps anywhere on the chart
- **THEN** the single point SHALL be selected (tooltip shown)
