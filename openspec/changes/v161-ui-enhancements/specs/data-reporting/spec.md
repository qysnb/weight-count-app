## MODIFIED Requirements

### Requirement: Period chips have proportional widths

The system SHALL display the five period filter chips (7日, 30日, 90日, 180日, 年度) with proportional widths where the "年度" chip is approximately 10% wider than each of the other four chips. Text within each chip SHALL be horizontally centered.

#### Scenario: Yearly chip is 10% wider
- **WHEN** the report screen renders
- **THEN** the period chip row SHALL use `Arrangement.SpaceBetween`
- **THEN** the four non-yearly chips SHALL use `Modifier.weight(1.0f)`
- **THEN** the "年度" chip SHALL use `Modifier.weight(1.1f)`
- **THEN** each chip's label text SHALL be center-aligned
