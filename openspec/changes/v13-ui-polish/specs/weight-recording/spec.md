## MODIFIED Requirements

### Requirement: Add dialog occupies 85% width with proper button heights

The system SHALL render the AddWeightDialog at 85% of screen width, with the time button height matching the date button height naturally via intrinsic sizing, avoiding excessive vertical space.

#### Scenario: Dialog width is 85%
- **WHEN** user taps the "记录" FAB
- **THEN** the dialog width SHALL be ~85% of screen width
- **THEN** the time button SHALL NOT be taller than the date button
- **THEN** the date and time buttons SHALL share the same height as determined by the date button's two-line content

#### Scenario: Time button height matches date button
- **WHEN** the AddWeightDialog is open
- **THEN** the parent Row of date/time buttons SHALL use `IntrinsicSize.Min` to constrain height
- **THEN** the time button's inner Column SHALL NOT use `Spacer(weight)` to force expansion
- **THEN** both buttons SHALL be the same height (no taller than needed for two lines of text)

## ADDED Requirements

### Requirement: Phone layout adaptation for AddWeightDialog

The system SHALL use `Modifier.fillMaxWidth(0.85f)` on the dialog Surface, capped at a reasonable maximum width to avoid stretching on larger screens.

#### Scenario: Width cap on large screens
- **WHEN** the dialog renders on a tablet or large-screen device
- **THEN** the dialog width SHALL NOT exceed 500dp
