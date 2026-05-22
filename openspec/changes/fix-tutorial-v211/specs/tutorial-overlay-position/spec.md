## ADDED Requirements

### Requirement: Tooltip card positioned with bottom 10% margin
The tutorial tooltip card SHALL be positioned with its bottom edge at 10% of screen height from the bottom, and its top edge dynamically determined by the remaining space.

#### Scenario: Card bottom position
- **WHEN** the tutorial tooltip card is visible
- **THEN** the bottom margin between the card and the screen bottom SHALL be 10% of the screen height
- **THEN** the card SHALL NOT extend above the screen top (top margin SHALL be at least 8.dp)

#### Scenario: Card padding implementation
- **WHEN** the tutorial is shown
- **THEN** the card padding SHALL use `Modifier.padding(start = 24.dp, end = 24.dp, bottom = screenHeight * 0.1f)` with screen height obtained from `BoxWithConstraints`
