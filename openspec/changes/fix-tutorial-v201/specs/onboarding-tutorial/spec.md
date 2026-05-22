## MODIFIED Requirements

### Requirement: First-run onboarding tutorial

The app SHALL present a multi-step onboarding tutorial on first launch to introduce new users to the three main modules (记录, 报表, 设置) by guiding them to interact with the UI themselves (no auto-created data).

#### Scenario: Tutorial triggers on first launch
- **WHEN** the app is launched for the first time (or tutorial data flag is `false`)
- **THEN** the app SHALL navigate to the 记录 tab
- **THEN** the tutorial SHALL display a semi-transparent overlay with step guidance

#### Scenario: Tutorial step 1 — 记录 tab introduction
- **WHEN** the tutorial step 1 is displayed
- **THEN** the overlay SHALL point to the bottom navigation bar's 记录 tab
- **THEN** text SHALL explain: "这是【记录】页面，您可以在这里添加和管理每天的体重数据"

#### Scenario: Tutorial step 2 — adding a record
- **WHEN** user advances to step 2
- **THEN** the overlay SHALL point to the floating action button (+)
- **THEN** text SHALL explain: "点击右下角的 + 按钮，填写体重、日期、标签等信息来添加一条记录"
- **THEN** the user SHALL be prompted to try adding a record themselves

#### Scenario: Tutorial step 3 — 报表 tab introduction
- **WHEN** user advances to step 3
- **THEN** the tutorial SHALL navigate to the 报表 tab
- **THEN** the overlay SHALL point to the chart area
- **THEN** text SHALL explain: "这是【报表】页面，您可以查看体重变化图表，切换周期或按标签筛选数据"
- **THEN** the user SHALL be encouraged to explore the chart filters

#### Scenario: Tutorial step 4 — 设置 tab introduction
- **WHEN** user advances to step 4
- **THEN** the tutorial SHALL navigate to the 设置 tab
- **THEN** the overlay SHALL point to the tag management and chart customization areas
- **THEN** text SHALL explain: "这是【设置】页面，您可以管理标签、自定义报表周期，或重新体验本教程"

#### Scenario: Tutorial step 5 — completion
- **WHEN** user advances to step 5
- **THEN** the overlay SHALL display a completion message: "教程已完成！现在您可以自由使用本应用了。"
- **WHEN** user taps "完成教程"
- **THEN** the tutorial SHALL dismiss

#### Scenario: Tutorial can be skipped
- **WHEN** the tutorial is active at any step
- **THEN** a "跳过" button SHALL be visible
- **WHEN** user taps "跳过"
- **THEN** the tutorial SHALL dismiss immediately (no cleanup needed)

### Requirement: Tutorial replay from Settings

The Settings page SHALL provide a way for users to replay the onboarding tutorial.

#### Scenario: "重新体验教程" in Settings
- **WHEN** user views the Settings page
- **THEN** a "重新体验教程" row SHALL appear under the "其他" section, above "关于"
- **WHEN** user taps "重新体验教程"
- **THEN** the full onboarding tutorial SHALL restart from step 1, first navigating to the 记录 tab
