## Why

The tutorial overlay disappears on first trigger because it was conditionally added to the composition tree via `if (showTutorial)`. Each `false → true` transition caused a dynamic composition addition, which was disrupted by Navigation backstack changes and parent recompositions. The solution is to always render the overlay in the tree and control visibility via an internal `visible` parameter.

Additionally, the first-use auto-trigger (removed in earlier versions) is restored: users who have not completed the tutorial see it automatically on first launch.

## What Changes

- TutorialOverlay is always composed (removed `if (showTutorial)` guard)
- Added `visible: Boolean` parameter — AnimatedVisibility controls show/hide internally
- Added `LaunchedEffect(visible)` to reset currentStep/isCollapsed when shown
- Restored auto-trigger: `LaunchedEffect(tutorialCompleted)` auto-shows tutorial if not completed
- Step 0 `navigateTo = null` — no auto-navigation on welcome step
- Version stays v2.3.0

## Capabilities

### Modified Capabilities

- `tutorial-overlay-architecture`: Always-composed overlay pattern. `visible` parameter instead of conditional composition. State persists across visibility changes.
- `tutorial-auto-trigger`: Restored first-launch detection via `tutorialCompleted` DataStore Flow. Auto-shows tutorial on initial launch.
- `tutorial-manual-trigger`: Button in Settings still works — sets `showTutorial = true`, overlay transitions to visible.

## Impact

- `TutorialOverlay.kt`: New signature `(visible: Boolean, onComplete, onNavigate)`. Removed `if (showTutorial)` from MainActivity. Restructured to always-composed `Box` with `AnimatedVisibility` inside.
- `MainActivity.kt`: Added `tutorialCompleted.collectAsState()`, `LaunchedEffect(tutorialCompleted)` for auto-trigger. Always renders TutorialOverlay.
