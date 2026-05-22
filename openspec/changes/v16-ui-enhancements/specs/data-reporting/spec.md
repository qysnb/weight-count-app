## MODIFIED Requirements

### Requirement: Period chips have spacing between them

The system SHALL display the five period filter chips (7日, 30日, 90日, 180日, 年度) distributed edge-to-edge across the full row width with proportional gaps between chips of approximately 15% of a chip's width. Text within each chip SHALL be horizontally centered.

#### Scenario: Gaps between chips
- **WHEN** the report screen renders
- **THEN** the period chip row SHALL use `Arrangement.SpaceBetween`
- **THEN** each FilterChip SHALL NOT use `Modifier.weight(1f)` (use intrinsic width)
- **THEN** each FilterChip SHALL have horizontal padding to create ~15% chip-width gaps
- **THEN** each chip's label text SHALL be center-aligned
- **THEN** the first chip ("7日") SHALL be left-aligned against the row edge
- **THEN** the last chip ("年度") SHALL be right-aligned against the row edge
