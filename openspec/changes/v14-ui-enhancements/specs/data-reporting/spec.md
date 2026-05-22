## MODIFIED Requirements

### Requirement: Chart date range header is enlarged

The system SHALL display the date range header text (e.g., "2026.6.1 - 2026.6.20") at the top center of the chart area using a font size approximately 10% larger than the previous bodySmall default (~14sp).

#### Scenario: Date range text rendered at larger size
- **WHEN** chart data is displayed
- **THEN** the center date range text SHALL use `titleSmall` typography (or equivalent ~14sp)
- **THEN** the text SHALL remain centered between the navigation arrows

### Requirement: Period selector removes label and adds 180-day chip

The system SHALL remove the "选择周期" label from the period selector row. The system SHALL add a "180日" filter chip between "90日" and "年度", corresponding to a 180-day semi-annual period.

#### Scenario: No label above period chips
- **WHEN** the report screen renders
- **THEN** the period chip row SHALL NOT contain "选择周期" text

#### Scenario: 180-day chip present
- **WHEN** the report screen renders
- **THEN** the period chip row SHALL contain "7日", "30日", "90日", "180日", "年度" chips in that order

#### Scenario: 180-day period selected
- **WHEN** user taps the "180日" chip
- **THEN** the chart SHALL display weight records from the last 180 days
- **THEN** the "180日" chip SHALL be visually selected

#### Scenario: 180-day period is configurable in settings
- **WHEN** user navigates to Settings
- **THEN** the 180-day period SHALL appear in the period configuration section
- **WHEN** user modifies the 180-day period's days or label
- **THEN** the change SHALL persist via DataStore and reflect in the report screen
