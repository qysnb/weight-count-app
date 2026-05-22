## MODIFIED Requirements

### Requirement: Chart date range header uses larger font

The system SHALL display the date range header text at the top center of the chart area using a font size approximately 15% larger than the previous `titleSmall` (~14sp → ~16sp).

#### Scenario: Date range text rendered at titleMedium
- **WHEN** chart data is displayed
- **THEN** the center date range text SHALL use `titleMedium` typography (or equivalent ~16sp)
- **THEN** the text SHALL remain centered between the navigation arrows

### Requirement: Period chips evenly distributed across row

The system SHALL display the five period filter chips (7日, 30日, 90日, 180日, 年度) evenly spaced across the full width of the report screen, using `Arrangement.SpaceBetween` and equal weight distribution.

#### Scenario: Chips fill the row width
- **WHEN** the report screen renders
- **THEN** the period chip row SHALL use `Arrangement.SpaceBetween`
- **THEN** each FilterChip SHALL use `Modifier.weight(1f)` for equal horizontal distribution
- **THEN** the first chip ("7日") SHALL be left-aligned and the last chip ("年度") SHALL be right-aligned
