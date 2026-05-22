## 1. Rewrite TutorialOverlay to Always-Composed Pattern

- [x] 1.1 Add `visible: Boolean` parameter to `TutorialOverlay`
- [x] 1.2 Wrap content in `Box(Modifier.fillMaxSize())` providing BoxScope for expand button alignment
- [x] 1.3 Use `AnimatedVisibility(visible = visible && !isCollapsed)` at root level
- [x] 1.4 Add `LaunchedEffect(visible)` to reset `currentStep = 0` and `isCollapsed = false`
- [x] 1.5 Guard `LaunchedEffect(currentStep)` with `if (visible)` to prevent navigation when hidden
- [x] 1.6 Move expand button outside AnimatedVisibility (inside wrapper Box)

## 2. Rewrite MainActivity for Always-Composed + Auto-Trigger

- [x] 2.1 Remove `if (showTutorial) { TutorialOverlay(...) }` — always render overlay
- [x] 2.2 Pass `visible = showTutorial` to TutorialOverlay
- [x] 2.3 Add `val tutorialCompleted by app.settingsDataStore.tutorialCompleted.collectAsState(initial = true)`
- [x] 2.4 Add `LaunchedEffect(tutorialCompleted)` with auto-trigger logic
- [x] 2.5 Add necessary imports (`LaunchedEffect`, `collectAsState`)
