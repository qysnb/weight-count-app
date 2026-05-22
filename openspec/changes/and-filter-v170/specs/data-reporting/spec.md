## MODIFIED Requirements

### Requirement: Report chart can filter by tag

The report page SHALL allow users to filter the displayed chart data by multiple tags using a toggle mechanism, independently from the date range selector. When one or more tags are selected, only weight records that have ALL of the selected tags SHALL be included in the chart. The tag filter and date range filter SHALL compose independently.

#### Scenario: Tag filter chip row with multi-select
- **WHEN** the report screen renders
- **THEN** a horizontal scrollable row of filter chips SHALL appear below the period chip row
- **THEN** the first chip SHALL display "全部" (all tags, active by default)
- **THEN** each subsequent chip SHALL display one user tag name
- **THEN** the chip row SHALL be horizontally scrollable if tags overflow the screen width

#### Scenario: Toggle tag selection
- **WHEN** a tag chip is not selected and user taps it
- **THEN** the chip SHALL appear selected (highlighted)
- **THEN** the "全部" chip SHALL appear unselected (deselected)
- **THEN** the chart SHALL update to include only records with that tag

#### Scenario: Toggle tag deselection
- **WHEN** a tag chip is selected and user taps it again
- **THEN** the chip SHALL appear unselected
- **THEN** the chart SHALL update to exclude records with only that tag

#### Scenario: Multiple tags selected with AND logic
- **WHEN** user selects two or more tags (e.g., "晨起" and "空腹")
- **THEN** the chart SHALL show only records matching ALL selected tags (AND logic)

#### Scenario: Select "全部" resets filter
- **WHEN** user taps "全部"
- **THEN** all tag chips SHALL appear unselected
- **THEN** the "全部" chip SHALL appear selected
- **THEN** the chart SHALL show all records for the selected period (no tag filter)

#### Scenario: No data after filtering
- **WHEN** user selects a tag combination that has no matching records
- **THEN** the chart SHALL display the existing empty state gracefully
