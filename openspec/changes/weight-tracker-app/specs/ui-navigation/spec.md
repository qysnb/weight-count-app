## ADDED Requirements

### Requirement: All UI text MUST be in Simplified Chinese

The system SHALL render every user-facing text string in Simplified Chinese (zh-CN). No English labels, hints, or button text SHALL appear in the default UI.

#### Scenario: Navigation tabs in Chinese
- **THEN** the three bottom tabs display as "记录", "报表", "设置"
- **THEN** no English fallback text is visible for any tab label

#### Scenario: All dialogs and messages in Chinese
- **WHEN** any dialog, snackbar, toast, or tooltip is shown
- **THEN** the text body SHALL be in Simplified Chinese (e.g., "确定", "取消", "请输入体重")

#### Scenario: Date/time formatting follows zh-CN conventions
- **WHEN** a date is displayed in the UI
- **THEN** the format SHALL follow Chinese convention (e.g., "2026年5月21日" or "05-21" depending on context)
- **THEN** the calendar SHALL display weekday headers as "一 二 三 四 五 六 日"

### Requirement: Bottom navigation bar with three tabs

The system SHALL display a bottom navigation bar occupying the bottom 8% of the screen with three tabs: "记录" (Record), "报表" (Report), "设置" (Settings).

#### Scenario: Bottom bar renders three tabs
- **THEN** the bottom navigation bar SHALL always be visible
- **THEN** each tab SHALL display an icon and label
- **THEN** the active tab SHALL be highlighted with the Material 3 accent color
- **THEN** the bottom bar SHALL occupy ~8% of screen height

#### Scenario: Tab switching navigates between screens
- **WHEN** user taps "报表" tab
- **THEN** the Report screen is displayed
- **WHEN** user taps "设置" tab
- **THEN** the Settings screen is displayed
- **WHEN** user taps "记录" tab
- **THEN** the Record screen is displayed

### Requirement: Navigation preserves state across tabs

The system SHALL preserve each tab's scroll position and input state when switching tabs.

#### Scenario: Scroll position preserved
- **WHEN** user scrolls the Record screen weight log halfway
- **WHEN** user switches to Report tab and back
- **THEN** the Record screen scroll position is restored
