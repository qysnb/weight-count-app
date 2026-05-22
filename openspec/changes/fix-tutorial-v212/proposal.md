## Why

The v2.1.1 tutorial refactor removed the auto-trigger in favor of manual activation from Settings, but the manual trigger doesn't start the tutorial correctly on first attempt. Additionally, the tooltip card is not properly bottom-aligned (10% margin), and several step texts have content errors. The collapse-brightness feature also remains broken due to `AnimatedVisibility` layout interference.

## What Changes

1. **Fix manual trigger**: In `MainActivity.kt`, remove redundant `navController.navigate` and `DataStore.write` from `onRestartTutorial` — let `TutorialOverlay`'s `LaunchedEffect` handle navigation
2. **Fix card position**: Add `Modifier.fillMaxSize()` to `AnimatedVisibility` so `Card.align(BottomCenter)` works, ensuring bottom 10% margin
3. **Fix collapse brightness**: Resolve root cause — with AnimatedVisibility fixed, dim overlay properly hides when collapsed
4. **Fix step 2 text**: Month nav = arrow symbols (← →), year nav = `<` `>`
5. **Fix step 7 text**: "长按或点击" → "点击"
6. **Version bump**: About screen → `v2.1.2`, build.gradle.kts → `2.1.2`

## Capabilities

### New Capabilities
- `tutorial-manual-trigger`: Fix Settings trigger starting tutorial
- `tutorial-overlay-layout`: Fix card bottom-alignment + collapse brightness restoration
- `tutorial-step-texts`: Fix step 2 calendar nav symbols and step 7 wording

### Modified Capabilities
- (no existing specs to modify)

## Impact

- `MainActivity.kt`: Simplify `onRestartTutorial` — remove navigation + DataStore write
- `TutorialOverlay.kt`: Fix `AnimatedVisibility` modifier (add `fillMaxSize`); update step 2 and step 7 texts
- `AboutScreen.kt`: `v2.0.2` → `v2.1.2`
- `build.gradle.kts`: `2.0.2` → `2.1.2`
