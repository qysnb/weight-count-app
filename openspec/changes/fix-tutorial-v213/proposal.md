## Why

Previous tutorial fixes (v2.1.2) failed to fully resolve three core bugs: the manual Settings trigger still doesn't start the tutorial on first attempt, the tooltip card is not bottom-aligned with 10% margin, and the dim overlay persists when the tooltip is collapsed. A more rigorous fix is needed.

## What Changes

1. **Fix manual trigger**: In `MainActivity.kt`, navigate to Record route FIRST (synchronously), then set `showTutorial = true`. Remove `navigateTo` from step 0 so TutorialOverlay doesn't double-navigate.
2. **Fix card position**: Wrap Card in explicit `Box(Modifier.fillMaxSize())` inside `AnimatedVisibility` to ensure `align(BottomCenter)` works reliably.
3. **Fix collapse brightness**: With the layout fix applied, verify dim overlay is correctly hidden when `isCollapsed = true`.
4. **Fix step 2 text**: Month arrows directly attached (`←上月→下月`), year brackets with space (`< 上年 > 下年`).
5. **Version bump**: About screen → `v2.1.3`, build.gradle.kts → `2.1.3`.

## Capabilities

### New Capabilities
- `tutorial-manual-trigger`: Fix Settings trigger — navigate first, show overlay second
- `tutorial-card-layout`: Fix card bottom-alignment with explicit Box wrapper in AnimatedVisibility
- `tutorial-step-texts`: Fix step 2 calendar nav symbols

### Modified Capabilities
- (no existing specs to modify)

## Impact

- `MainActivity.kt`: Restore navigation in `onRestartTutorial`, set `showTutorial = true` after
- `TutorialOverlay.kt`: Remove `navigateTo` from step 0; wrap Card in `Box(Modifier.fillMaxSize())` inside AnimatedVisibility; fix step 2 text
- `AboutScreen.kt`: `v2.1.2` → `v2.1.3`
- `build.gradle.kts`: `2.1.2` → `2.1.3`
