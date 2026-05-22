## 1. Fix Manual Tutorial Trigger

- [x] 1.1 `onRestartTutorial` simplified to `showTutorial = true` — removed DataStore write + redundant navigation

## 2. Fix Card Layout + Collapse Brightness

- [x] 2.1 Added `Modifier.fillMaxSize()` to `AnimatedVisibility` — Card `.align(BottomCenter)` now works correctly
- [x] 2.2 Dim overlay (`if (!isCollapsed)` box) already correctly hides when collapsed — no change needed

## 3. Fix Step Texts

- [x] 3.1 Step 2: `< 上月 > 下月` → `← 上月 → 下月`
- [x] 3.2 Step 7: "您可以长按或点击一条已有记录" → "您可以点击一条已有记录"

## 4. Version Bump

- [x] 4.1 AboutScreen: `"v2.1.1"` → `"v2.1.2"`
- [x] 4.2 `build.gradle.kts`: `"2.1.1"` → `"2.1.2"`
