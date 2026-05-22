## ADDED Requirements

### Requirement: Report chart can filter by tag

The report page SHALL allow users to filter the displayed chart data by a single tag, independently from the date range selector. When a tag is selected, only weight records that have that tag SHALL be included in the chart. The tag filter and date range filter SHALL compose independently.

#### Scenario: Tag filter chip row displayed below period chips
- **WHEN** the report screen renders
- **THEN** a horizontal scrollable row of filter chips SHALL appear below the period chip row
- **THEN** the first chip SHALL display "全部" (all tags, selected by default)
- **THEN** each subsequent chip SHALL display one user tag name
- **THEN** the chip row SHALL be horizontally scrollable if tags overflow the screen width

#### Scenario: Select tag filters chart data
- **WHEN** user taps a tag chip (e.g., "饭前")
- **THEN** the chart SHALL update to show only weight records from the selected period that have the "饭前" tag
- **THEN** the selected tag chip SHALL appear highlighted
- **THEN** the "全部" chip SHALL appear unselected

#### Scenario: Select "全部" resets filter
- **WHEN** user taps "全部"
- **THEN** the chart SHALL show all records for the selected period (no tag filter)
- **THEN** all tag chips SHALL appear unselected

#### Scenario: No data after filtering
- **WHEN** user selects a tag + date range combination that has no matching records
- **THEN** the chart SHALL display the existing empty state (no crash, graceful fallback)
