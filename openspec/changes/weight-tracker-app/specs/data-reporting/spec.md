## ADDED Requirements

### Requirement: Chart UI and labels use Simplified Chinese

The system SHALL render all report screen text in Simplified Chinese, including axis labels, period selector buttons, date range picker, tooltips, and empty-state messages.

#### Scenario: Period buttons in Chinese
- **THEN** the period selector buttons display as "7日", "30日", "90日", "年度"
- **THEN** the "选择周期" label SHALL be on the same row as the period chips

#### Scenario: Date range picker in Chinese
- **WHEN** user toggles the date range selector
- **THEN** a "选择范围" section SHALL appear below the period selector row
- **THEN** two date picker buttons SHALL be shown: start date and end date
- **THEN** the start date button SHALL display "yyyy年M月d日" format
- **THEN** the end date button SHALL display "yyyy年M月d日" format

#### Scenario: Empty state in Chinese
- **WHEN** no data exists for the selected period
- **THEN** the chart area displays "暂无数据"

#### Scenario: Tooltip content in Chinese
- **WHEN** a data point is tapped
- **THEN** the tooltip SHALL display at least three lines: "yyyy年M月d日" (line 1), "HH:mm  xx.x kg" (line 2), tag names (line 3+)
- **THEN** tag names SHALL display max 2 per line, wrapping to additional lines if more than 2 tags exist

#### Scenario: Axis labels in Chinese
- **THEN** the y-axis grid labels SHALL show weight values with "kg" suffix
- **THEN** month labels on the x-axis SHALL use Chinese format (e.g., "1月", "2月")

### Requirement: Weight line chart shows trend over selectable period

The system SHALL render a line chart of weight over time. The x-axis SHALL be date/time and the y-axis SHALL be weight in kg. The top ~60% of the Report screen SHALL be the chart area, with selector controls below.

#### Scenario: Chart renders with 7-day data
- **WHEN** user taps "7日" or selects a 7-day date range
- **THEN** the line chart displays weight records from the selected period
- **WHEN** there are 5 records in that window
- **THEN** exactly 5 data points appear on the chart connected by a smooth line

#### Scenario: Chart renders with 30-day data
- **WHEN** user taps "30日"
- **THEN** the line chart displays weight records from the last 30 days
- **WHEN** there are 12 records
- **THEN** 12 data points appear

#### Scenario: Chart renders with 90-day data
- **WHEN** user taps "90日"
- **THEN** the line chart displays records from the last 90 days

#### Scenario: Chart renders with annual data
- **WHEN** user taps "年度"
- **THEN** the line chart displays records from the last 365 days

#### Scenario: Chart renders with custom date range
- **WHEN** user sets start date to 2026-06-01 and end date to 2026-06-30
- **THEN** the chart displays records only between those two dates inclusive

#### Scenario: No data for selected period
- **WHEN** there are no records in the selected period or range
- **THEN** the chart area shows "暂无数据" centered text
- **THEN** no chart is rendered

### Requirement: Chart is interactive and accessible

The system SHALL allow users to tap on data points to see the exact weight, date, time, and associated tags.

#### Scenario: Show multi-line tooltip on data point tap
- **WHEN** user taps a data point on the chart
- **THEN** a tooltip appears showing:
  - Line 1: date in "yyyy年M月d日" format
  - Line 2: time and weight e.g., "08:30  70.5 kg"
  - Line 3+: tag names, max 2 per line, e.g., "饭前 饭后" on one line, "自定义标签1 自定义标签2" on next

### Requirement: Chart x-axis deduplicates daily labels

When multiple records exist on the same day, the x-axis SHALL display only one date label per day, while still plotting all data points for that day.

#### Scenario: Multiple same-day records show one x-axis label
- **WHEN** there are 3 records on 2026-05-21 at different times
- **THEN** exactly one "05-21" label appears on the x-axis for that day
- **THEN** all 3 data points are plotted on the chart

### Requirement: Report layout groups period selector with label

The "选择周期" label and the period filter chips SHALL occupy the same horizontal row, with a new "选择范围" section below for manual date range selection.

#### Scenario: Period label and chips in one row
- **THEN** the text "选择周期" SHALL be on the same horizontal line as the filter chips (7日, 30日, 90日, 年度)

#### Scenario: Date range section below period
- **THEN** a "选择范围" label SHALL be displayed below the period chips
- **THEN** two buttons SHALL follow: one for start date, one for end date
- **THEN** tapping a date button SHALL open a DatePickerDialog
- **THEN** the chart SHALL update to only show data within the selected range
- **THEN** if both dates are set, the period chips SHALL be visually deselected

### Requirement: Chart axis labels are human-readable

The system SHALL format the x-axis with localized date labels and y-axis with kg labels.

#### Scenario: X-axis date formatting
- **WHEN** the 7-day view is active
- **THEN** x-axis labels show short date (e.g., "05/15", "05/16", "05/17")
- **WHEN** the annual view is active
- **THEN** x-axis labels show month names (e.g., "1月", "2月")

#### Scenario: Y-axis weight formatting
- **THEN** y-axis labels show weight values with "kg" suffix
- **THEN** the axis range auto-adjusts with ~10% padding above max and below min
