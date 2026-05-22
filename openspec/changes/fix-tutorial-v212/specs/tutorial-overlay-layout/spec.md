## ADDED Requirements

### Requirement: Tooltip card bottom-aligned with 10% margin
The tutorial tooltip card SHALL be positioned at the bottom of the screen with a bottom margin equal to 10% of screen height, and the dim overlay SHALL be hidden when the tooltip is collapsed.

#### Scenario: Card at screen bottom
- **WHEN** the tutorial tooltip is visible
- **THEN** the card SHALL be aligned to `Alignment.BottomCenter` of the `BoxWithConstraints`
- **THEN** the bottom edge of the card SHALL be 10% of screen height above the screen bottom

#### Scenario: Dim overlay hidden on collapse
- **WHEN** user taps the minimize button (`_`)
- **THEN** the dim overlay (`Color.Black.copy(alpha = 0.6f)`) SHALL be removed
- **THEN** only the expand button SHALL remain visible
