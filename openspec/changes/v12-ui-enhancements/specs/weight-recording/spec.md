## ADDED Requirements

### Requirement: Calendar has year navigation buttons

The system SHALL provide previous-year and next-year arrow buttons in the calendar header Row, alongside the existing month navigation, allowing the user to jump by full years instead of scrolling month-by-month.

#### Scenario: Year buttons visible in calendar header
- **THEN** the calendar header Row SHALL display five controls: [<<] [<] [yyyy年M月] [>] [>>]
- **THEN** the [<<] button SHALL decrement `displayYear` by 1
- **THEN** the [>>] button SHALL increment `displayYear` by 1
- **THEN** `displayMonth` SHALL remain unchanged when navigating years
- **THEN** the calendar grid SHALL reload with weight badges for the new year-month

#### Scenario: Year navigation preserves scroll position
- **WHEN** user taps [>>] from May 2026 to May 2027
- **THEN** the weight log list SHALL show records from May 2027
- **THEN** the weight log list scroll position SHALL reset to top

## MODIFIED Requirements

### Requirement: Add dialog has responsive Dialog layout with matched button heights

The system SHALL render the AddWeightDialog as a proper Dialog (not in-page overlay) occupying approximately 65% of screen width, with rounded corners, and the time button height matched to the date button height.

#### Scenario: Dialog is a standalone window
- **WHEN** user taps the "记录" FAB
- **THEN** a new Dialog window appears overlaid on the current screen
- **THEN** the Dialog width SHALL be ~65% of screen width (max 400dp)
- **THEN** the Dialog corners SHALL use MaterialTheme.shapes.extraLarge or equivalent rounding

#### Scenario: Date/time button heights are equal
- **WHEN** the AddWeightDialog is open
- **THEN** the date button displays "2026年\n5月21日" across two lines
- **THEN** the time button SHALL have exactly the same height as the date button
- **THEN** the time button displays "08:30" centered vertically within its bounds
- **THEN** the date and time buttons SHALL be placed side by side in a Row with equal weight

#### Scenario: Existing requirements preserved
- **THEN** all validation, tag selection, confirm/cancel, and delete behaviors SHALL remain unchanged from v1.1
