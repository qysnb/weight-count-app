## ADDED Requirements

### Requirement: Step 2 uses correct arrow symbols
The calendar navigation step SHALL use arrow symbols (← →) for month navigation and angle brackets (`<` `>`) for year navigation.

#### Scenario: Step 2 text
- **WHEN** user views step 2 (日历导航)
- **THEN** the text SHALL display `← 上月 → 下月` for month navigation
- **THEN** the text SHALL display `< 上年 > 下年` for year navigation

### Requirement: Step 7 uses correct wording
The delete-record step SHALL use "点击" instead of "长按或点击".

#### Scenario: Step 7 text
- **WHEN** user views step 7 (删除记录)
- **THEN** the description SHALL say "点击一条已有记录" instead of "长按或点击一条已有记录"
