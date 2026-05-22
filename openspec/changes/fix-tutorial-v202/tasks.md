## 1. Fix Tutorial Trigger Race Condition

- [x] 1.1 Replaced `LaunchedEffect(tutorialCompleted)` with `LaunchedEffect(Unit)` collecting DataStore Flow directly via `.filter { !it }.first()` — eliminates null-initial race

## 2. Add Collapse/Expand to TutorialOverlay

- [x] 2.1 Added `var isCollapsed by remember { mutableStateOf(false) }`
- [x] 2.2 Added `IconButton` at top-left of card with `"_"` text, sets `isCollapsed = true`
- [x] 2.3 Added `Box` with `CircleShape` + `"···"` at `BottomEnd`, visible only when collapsed, sets `isCollapsed = false`
- [x] 2.4 Wrapped `Card` in `AnimatedVisibility(visible = !isCollapsed)` with fade+slide
- [x] 2.5 `LaunchedEffect(currentStep)` resets `isCollapsed = false`

## 3. Add New Tutorial Steps + Fix Navigation Order

- [x] 3.1 Added step 2: "日历导航" — describes 4 arrows for year/month switching
- [x] 3.2 Added step 5: "图表数据点" — describes tapping data points + arrow switching
- [x] 3.3 Navigation moved to `LaunchedEffect(currentStep)`: fires on every step change, removed from button handler
- [x] 3.4 `onClick` handler simplified: `if (currentStep < steps.size - 1) currentStep++ else onComplete()`

## 4. Version Bump

- [x] 4.1 AboutScreen `"v2.0.1"` → `"v2.0.2"`
- [x] 4.2 `app/build.gradle.kts` `"2.0.1"` → `"2.0.2"`
