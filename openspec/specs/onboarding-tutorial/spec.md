# Onboarding Tutorial

## Manual-only trigger from Settings

The tutorial SHALL only be triggered manually from the Settings page. No automatic first-launch trigger SHALL exist.

### Scenario: Manual trigger from Settings
- **WHEN** user navigates to Settings → 关于 → taps "使用教程"
- **THEN** `showTutorial` is set to `true`
- **AND** the tutorial overlay appears on the current screen with step 0

### Scenario: Tutorial completed
- **WHEN** user taps "完成教程" on the final step, or "跳过" on any step
- **THEN** `showTutorial` is set to `false`
- **AND** `tutorialCompleted` flag is persisted to DataStore (`true`)
- **AND** the overlay is dismissed

## Overlay architecture

### Always-composed pattern
The `TutorialOverlay` composable SHALL always be present in the composition tree of `MainScreen`. Visibility SHALL be controlled via a `visible: Boolean` parameter. The overlay uses direct `if (visible)` control (no `AnimatedVisibility`).

### State reset on visibility change
- **WHEN** `visible` changes from `false` to `true`
- **THEN** `currentStep` resets to 0
- **AND** `isCollapsed` resets to `false`

### Touch consumption
The dim overlay SHALL consume all touches via `clickable` to prevent unintended interactions with the content underneath.

## Steps

### Step 0: Welcome (navigateTo = null)
- Title: "欢迎使用体重记录"
- Description: Introduces 记录, 报表, 设置 modules + collapse instructions
- No navigation on this step

### Step 1: Calendar navigation (navigateTo = Screen.Record)
- Title: "日历导航"
- Content: Arrow button explanations for month/year switching
- Auto-navigates to Record page

### Step 2: Add record (navigateTo = Screen.Record)
- Title: "添加记录"
- Content: FAB button explanation, user prompted to add two records

### Step 3: View report (navigateTo = Screen.Report)
- Title: "查看报表"
- Content: Chart period switching, date range, tag filter explanations
- Auto-navigates to Report page

### Step 4: Chart data points (navigateTo = Screen.Report)
- Title: "图表数据点"
- Content: Click data points to see details, ← → navigation arrows
- Auto-navigates to Report page

### Step 5: Settings & customization (navigateTo = Screen.Settings)
- Title: "设置与自定义"
- Content: Tag management, custom periods, tutorial replay
- Auto-navigates to Settings page

### Step 6: Delete record (navigateTo = Screen.Record)
- Title: "删除记录"
- Content: Click record → action menu → delete
- Auto-navigates to Record page

### Step 7: Completion (navigateTo = null)
- Title: "教程完成"
- Content: Tutorial completion message, reminder to revisit via Settings

## Collapse/Expand

### Collapse button
- **WHEN** user taps the `_` button at the Card's top-left
- **THEN** `isCollapsed` = `true`
- **AND** the overlay hides
- **AND** a floating `···` button appears at the bottom-right corner

### Expand button
- **WHEN** user taps the `···` button
- **THEN** `isCollapsed` = `false`
- **AND** the overlay reappears at the same step

## Navigation behavior

Step navigation uses `LaunchedEffect(currentStep)`. When the step changes:
- `isCollapsed` resets to `false`
- If the step has a `navigateTo` route, navigation fires via `navController.navigate()` with `popUpTo(startDestination) { saveState = true }`, `launchSingleTop = true`, `restoreState = true`

## Layout
- Card is positioned at the bottom of the screen with 10% vertical margin from bottom edge
- Card has `RoundedCornerShape(16.dp)`, elevation 8.dp
- Dim overlay uses `Color.Black.copy(alpha = 0.6f)` with `animateFloatAsState` fade-in
- Card content is scrollable via `verticalScroll`
- Buttons: "跳过" (left), "上一步" (middle-left, hidden on step 0), "下一步"/"完成教程" (right)
- Step counter: "第 N/8 步" above title
