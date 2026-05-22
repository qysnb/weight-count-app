## ADDED Requirements

### Requirement: First-run onboarding tutorial

The app SHALL present a multi-step onboarding tutorial on first launch to introduce new users to the three main modules (记录, 报表, 设置), create sample weight data, demonstrate the chart, and guide cleanup.

#### Scenario: Tutorial triggers on first launch

- **WHEN** the app is launched for the first time
- **THEN** the tutorial SHALL automatically start after the main screen is displayed

#### Scenario: Tutorial step — introduce recording

- **WHEN** the tutorial step 1 is displayed
- **THEN** a semi-transparent overlay SHALL highlight the record tab area
- **THEN** text SHALL explain how to add a weight record with optional date/time/tags

#### Scenario: Tutorial step — create sample record 1

- **WHEN** user advances to step 2
- **THEN** the system SHALL automatically create a sample weight record (weight=70.0kg, tag="晨起", timestamp=current date 08:00)
- **THEN** a success message SHALL display confirming the sample was added

#### Scenario: Tutorial step — create sample record 2

- **WHEN** user advances to step 3
- **THEN** the system SHALL automatically create a second sample weight record (weight=70.5kg, tag="睡前", timestamp=current date 22:00)
- **THEN** a success message SHALL display confirming the sample was added

#### Scenario: Tutorial step — introduce chart

- **WHEN** user advances to step 4
- **THEN** the tutorial SHALL navigate to the 报表 tab
- **THEN** a highlight overlay SHALL point to the chart area showing the two sample data points
- **THEN** text SHALL explain how to read the chart and use period/tag filters

#### Scenario: Tutorial step — introduce settings

- **WHEN** user advances to step 5
- **THEN** the tutorial SHALL navigate to the 设置 tab
- **THEN** a highlight overlay SHALL point to the tag management and chart customization areas
- **THEN** text SHALL explain how to manage tags and customize periods

#### Scenario: Tutorial step — cleanup sample data

- **WHEN** user advances to step 6
- **THEN** a dialog SHALL display with a "删除示例数据" button
- **WHEN** user taps the button
- **THEN** all sample records SHALL be deleted from the database
- **THEN** a confirmation message SHALL display
- **THEN** the tutorial SHALL end

#### Scenario: Tutorial can be skipped

- **WHEN** the tutorial is active at any step
- **THEN** a "跳过" button SHALL be visible
- **WHEN** user taps "跳过"
- **THEN** any existing sample records SHALL be deleted
- **THEN** the tutorial SHALL dismiss

### Requirement: Tutorial replay from Settings

The Settings page SHALL provide a way for users to replay the onboarding tutorial.

#### Scenario: "重新体验教程" in Settings

- **WHEN** user views the Settings page
- **THEN** a "重新体验教程" row SHALL appear under the "其他" section, above "关于"
- **WHEN** user taps "重新体验教程"
- **THEN** the full onboarding tutorial SHALL restart from step 1
