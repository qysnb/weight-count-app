## ADDED Requirements

### Requirement: Chart tooltip has adequate bottom margin

The system SHALL render the chart tooltip with sufficient padding below the last text line to prevent visual crowding against the tooltip background border.

#### Scenario: Tooltip bottom padding
- **WHEN** a data point is tapped and tooltip is shown
- **THEN** the tooltip background SHALL include 0.5 line-height extra padding below the last tag line (or time line if no tags)
- **THEN** the bottom of the last text line SHALL be at least 1.5 line-heights from the tooltip bottom edge

### Requirement: X-axis labels are evenly distributed across time range

The system SHALL render x-axis date labels at evenly-spaced intervals across the full time range (min timestamp to max timestamp), regardless of where actual data points fall, to prevent label overlap.

#### Scenario: Labels at evenly spaced intervals
- **WHEN** the chart displays a 7-day range (2026-06-01 to 2026-06-07)
- **THEN** x-axis labels SHALL appear at evenly spaced time intervals (e.g., 06-01, 06-03, 06-05, 06-07)
- **THEN** labels SHALL NOT be tied to individual data point positions

#### Scenario: Number of labels varies by range
- **WHEN** the selected period is 7-day
- **THEN** up to 5 evenly-spaced labels are shown
- **WHEN** the selected period is 30-day
- **THEN** up to 6 evenly-spaced labels are shown
- **WHEN** the selected period is 90-day or annual
- **THEN** up to 8 evenly-spaced labels are shown

#### Scenario: Custom date range distribution
- **WHEN** user sets a custom range of 15 days
- **THEN** 6 evenly-spaced labels SHALL be shown across the 15-day range
- **THEN** the first label SHALL correspond to the range start date
- **THEN** the last label SHALL correspond to the range end date

### Requirement: Date range selection in single row with boundary fix

The system SHALL display the "选择范围" label and two date picker buttons on the same horizontal row, and normalize date boundaries so start date covers from 00:00:00.000 and end date covers until 23:59:59.999 of their respective days.

#### Scenario: Label and buttons in one row
- **WHEN** the Report screen renders below the period chips
- **THEN** "选择范围" text SHALL be on the same horizontal line as the start/end date buttons
- **THEN** layout SHALL be: `Text("选择范围") | Spacer | OutlinedButton("起始日期") | OutlinedButton("结束日期")`
- **THEN** tapping a date button SHALL open a DatePickerDialog

#### Scenario: Start date normalized to day start
- **WHEN** user picks start date 2026-06-01 in the DatePicker
- **THEN** the stored `rangeStart` SHALL be 2026-06-01 00:00:00.000 in local timezone
- **THEN** records at exactly 2026-06-01 00:00:00.000 SHALL be included

#### Scenario: End date normalized to day end
- **WHEN** user picks end date 2026-06-02 in the DatePicker
- **THEN** the stored `rangeEnd` SHALL be 2026-06-02 23:59:59.999 in local timezone
- **THEN** records at any time on 2026-06-02 SHALL be included
- **THEN** records at 2026-06-03 00:00:00.000 SHALL be excluded

#### Scenario: Both dates required for range filter
- **WHEN** only start date is set but end date is not set
- **THEN** the chart SHALL NOT filter by custom range (continue showing period-based data)
- **WHEN** only end date is set but start date is not set
- **THEN** the chart SHALL NOT filter by custom range
