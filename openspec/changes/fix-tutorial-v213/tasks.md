## 1. Fix Manual Tutorial Trigger

- [x] 1.1 `onRestartTutorial` now navigates to Record route FIRST, then sets `showTutorial = true`
- [x] 1.2 Step 0 `navigateTo` set to `null` — no duplicate navigation from LaunchedEffect

## 2. Fix Card Layout in AnimatedVisibility

- [x] 2.1 Card wrapped in `Box(Modifier.fillMaxSize())` inside AnimatedVisibility — `align(BottomCenter)` now reliably works
- [x] 2.2 Dim overlay already correctly gates on `if (!isCollapsed)` — no change needed

## 3. Fix Step 2 Text

- [x] 3.1 `← 上月 → 下月` → `←上月→下月` (month arrows with no space)

## 4. Version Bump

- [x] 4.1 AboutScreen: `"v2.1.2"` → `"v2.1.3"`
- [x] 4.2 `build.gradle.kts`: `"2.1.2"` → `"2.1.3"`
