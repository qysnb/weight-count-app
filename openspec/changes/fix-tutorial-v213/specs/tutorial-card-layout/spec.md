## ADDED Requirements

### Requirement: Card reliably bottom-aligned with 10% bottom margin
The tutorial tooltip card SHALL be reliably positioned at the bottom of the screen with a bottom margin of 10% of screen height. The `AnimatedVisibility` wrapping the card SHALL contain a full-size `Box` that provides `BoxScope` for the Card's `align(BottomCenter)`.

#### Scenario: Card position
- **WHEN** the tutorial tooltip is visible
- **THEN** the card SHALL be inside `AnimatedVisibility` → `Box(Modifier.fillMaxSize())` → `Card(Modifier.align(Alignment.BottomCenter))`
- **THEN** the Card bottom edge SHALL be offset from screen bottom by 10% of `BoxWithConstraints.maxHeight`

### Requirement: Dim overlay hidden when collapsed
When the tutorial tooltip is collapsed, the dim overlay SHALL be completely hidden — no partial or residual dimming.

#### Scenario: Collapse removes dim
- **WHEN** user taps the minimize button
- **THEN** the dim overlay SHALL disappear immediately
- **THEN** only the expand button SHALL be visible with no background dimming
