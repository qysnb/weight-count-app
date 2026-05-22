## ADDED Requirements

### Requirement: Step 2 uses correct calendar nav symbols
The calendar navigation step SHALL use arrow symbols (`←` `→`) directly attached to month names (no space) and angle brackets (`<` `>`) followed by a space for year names.

#### Scenario: Step 2 text
- **WHEN** user views step 2 (日历导航)
- **THEN** month navigation line SHALL display `←上月→下月` (arrows directly attached, no space)
- **THEN** year navigation line SHALL display `< 上年 > 下年` (brackets with space after)
