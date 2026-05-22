## 1. Fix Manual Tutorial Trigger Race Condition

- [x] 1.1 Removed `navController.navigate(Screen.Record.route)` from `onRestartTutorial` — only `showTutorial = true`
- [x] 1.2 Step 0 `navigateTo = Screen.Record.route` — LaunchedEffect(0) navigates to Record

## 2. Fix Step Navigation Tab Switching

- [x] 2.1 Step 1 (日历导航) `navigateTo = Screen.Record.route`
- [x] 2.2 Step 2 (添加记录) `navigateTo = Screen.Record.route`
- [x] 2.3 Step 4 (图表数据点) `navigateTo = Screen.Report.route`

## 3. Update Step 3 and Step 6 Texts

- [x] 3.1 Step 3: "右下角的 + 按钮" → "下方中间的 + 按钮"; "添加一条" → "添加两条"
- [x] 3.2 Step 6: "使用本教程" → "再次体验本教程"

## 4. Version Bump

- [x] 4.1 AboutScreen: `"v2.1.3"` → `"v2.2.0"`
- [x] 4.2 `build.gradle.kts`: `"2.1.3"` → `"2.2.0"`
