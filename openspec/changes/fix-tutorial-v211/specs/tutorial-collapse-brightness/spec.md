## ADDED Requirements

### Requirement: Dim overlay hidden when tooltip collapsed
When the tutorial tooltip is collapsed (minimized to bottom-right), the semi-transparent black dim overlay SHALL be hidden, restoring the screen to normal brightness.

#### Scenario: Collapse removes dim
- **WHEN** user taps the minimize button (`_`) on the tooltip card
- **THEN** the black dim overlay (`Color.Black.copy(alpha = 0.6f)`) SHALL be removed
- **THEN** the screen SHALL appear at normal brightness
- **THEN** only the floating expand button (`···`) SHALL remain visible

#### Scenario: Expand restores dim
- **WHEN** user taps the floating expand button (`···`)
- **THEN** the black dim overlay SHALL reappear
- **THEN** the tooltip card SHALL reappear within the dimmed area
