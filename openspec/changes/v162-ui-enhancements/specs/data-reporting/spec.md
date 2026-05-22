## MODIFIED Requirements

### Requirement: Period chips have proportional spacing

The system SHALL display the five period filter chips (7日, 30日, 90日, 180日, 年度) with equal dynamic spacing around each chip. Each chip SHALL use its intrinsic width based on its label text. Chips MUST NOT be forced to fill the full row width via `Modifier.weight()` or similar layout stretching.

#### Scenario: Period chip row uses SpaceEvenly
- **WHEN** the report screen renders
- **THEN** the period chip row SHALL use `Arrangement.SpaceEvenly`
- **THEN** each chip SHALL NOT use `Modifier.weight()` — chips wrap their intrinsic content width
- **THEN** remaining horizontal space SHALL be distributed equally before, between, and after each chip
- **THEN** each chip's label text SHALL be center-aligned within the chip
