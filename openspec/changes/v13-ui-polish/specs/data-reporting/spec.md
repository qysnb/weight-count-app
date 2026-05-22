## ADDED Requirements

### Requirement: Chart tooltip has prev/next navigation arrows

The system SHALL display left and right arrow buttons above the chart Canvas for cycling through adjacent data points. The center area between the arrows SHALL show the date range of the currently displayed data.

#### Scenario: Prev/next arrows visible above chart
- **WHEN** chart data is displayed
- **THEN** a Row SHALL be rendered above the chart Canvas containing:
  - Left IconButton (ArrowBack) for previous data point
  - Center Text showing date range in "yyyy.M.d - yyyy.M.d" format
  - Right IconButton (ArrowForward) for next data point

#### Scenario: Navigate to previous data point
- **WHEN** user taps the left arrow button while a data point is selected (tooltip visible)
- **THEN** the selection moves to the previous data point in the sorted list
- **WHEN** the first data point is selected and user taps left
- **THEN** the selection wraps to the last data point

#### Scenario: Navigate to next data point
- **WHEN** user taps the right arrow button while a data point is selected
- **THEN** the selection moves to the next data point
- **WHEN** the last data point is selected and user taps right
- **THEN** the selection wraps to the first data point

#### Scenario: Date range text in center
- **WHEN** chart data exists
- **THEN** the center text SHALL display "yyyy.M.d - yyyy.M.d" based on the first and last data point timestamps
- **WHEN** a custom range is active
- **THEN** the center text SHALL display "yyyy.M.d - yyyy.M.d" based on the rangeStart and rangeEnd values

### Requirement: Date range buttons show static text only

The system SHALL display the date range selection buttons with fixed "起始日期" and "结束日期" labels regardless of whether dates have been selected. The actual selected dates SHALL be displayed at the top center of the chart area instead.

#### Scenario: Buttons show static text
- **WHEN** no date range is selected
- **THEN** the start date button SHALL display "起始日期"
- **THEN** the end date button SHALL display "结束日期"
- **WHEN** user selects a date range
- **THEN** the buttons SHALL continue to display "起始日期" and "结束日期" (no substitution)
- **THEN** the selected date range SHALL be visible in the chart header center area

#### Scenario: Buttons aligned with period chips
- **THEN** the start date button SHALL be left-aligned with the "7日" chip above
- **THEN** the end date button SHALL be right-aligned with the "年度" chip above
- **THEN** both buttons SHALL be wide enough to display "起始日期" and "结束日期" without text wrapping
