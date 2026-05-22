## ADDED Requirements

### Requirement: Collapse overlay tooltip
The tutorial overlay SHALL provide a collapse/expand mechanism so the user can temporarily hide the tooltip card to interact with the UI beneath.

#### Scenario: Collapse tooltip
- **WHEN** user taps the minimize button (top-left corner of the tooltip card, labeled `_`)
- **THEN** the tooltip card SHALL animate out and a small floating expand button SHALL appear at the bottom-right corner of the screen

#### Scenario: Expand tooltip
- **WHEN** user taps the floating expand button at the bottom-right
- **THEN** the tooltip card SHALL animate back into its original position, and the floating button SHALL disappear

#### Scenario: Collapse state resets on step change
- **WHEN** user navigates to the next or previous tutorial step
- **THEN** the tooltip SHALL automatically expand (collapse state reset to false)

#### Scenario: Overlay dim persists during collapse
- **WHEN** the tooltip is collapsed
- **THEN** the semi-transparent black overlay SHALL remain visible (user has not dismissed the tutorial)

### Requirement: Minimize button accessibility
The minimize button SHALL be clearly visible against the card surface and labeled with a simple icon or text (`_`) indicating collapse.

#### Scenario: Button position
- **WHEN** the tooltip card is rendered
- **THEN** the minimize button SHALL be positioned at the top-left corner of the card
- **THEN** the minimize button SHALL NOT overlap with step counter text
