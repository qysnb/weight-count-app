## 1. Remove Auto-Trigger Tutorial

- [x] 1.1 Deleted `LaunchedEffect(Unit)` block + removed unused `filter`/`first`/`LaunchedEffect` imports from MainActivity

## 2. Rename Settings Button

- [x] 2.1 Changed "重新体验教程" → "使用教程" in SettingsScreen.kt

## 3. Rewrite TutorialOverlay Layout + Content

- [x] 3.1 Used `BoxWithConstraints` with `maxHeight * 0.1f` as bottom padding for card
- [x] 3.2 Split overlay: dim background rendered only when `!isCollapsed`; card wrapped in `AnimatedVisibility`
- [x] 3.3 Step 2: `← 上年 → 下年` → `< 上年  > 下年`
- [x] 3.4 Step 3: removed `！` from add-record description
- [x] 3.5 Added step 6 (删除记录) between 设置与自定义 and 教程完成, navigateTo = Record
- [x] 3.6 Updated "重新体验本教程" → "使用本教程" in step 5 & 7

## 4. Fix Single-Point Chart

- [x] 4.1 Moved grid drawing before size check; added single-point branch (center dot + date label + tooltip)
- [x] 4.2 Tap detection: single-point tap toggles selection (index 0 / -1)

## 5. Version Bump

- [x] 5.1 AboutScreen: `"v2.0.2"` → `"v2.1.1"`
- [x] 5.2 `build.gradle.kts`: `"2.0.2"` → `"2.1.1"`
