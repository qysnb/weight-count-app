## Why

The tutorial overlay disappears on first trigger because step 0's auto-navigation (via LaunchedEffect) calls `onNavigate(Screen.Record.route)` which uses `popUpTo(start) + restoreState = true`. This backstack manipulation disrupts the overlay's initial composition, making it visually "disappear." The second attempt works because the backstack is already stable.

## What Changes

- Remove `navController.navigate(Record)` from `onRestartTutorial` — no pre-navigation
- Remove step 0's auto-navigation (`navigateTo = null`) — the overlay appears where the user is (Settings), and clicking "下一步" navigates to Record (step 1) cleanly
- Version stays at v2.3.0

## Capabilities

### Modified Capabilities

- `tutorial-manual-trigger`: Remove all initial auto-navigation. Step 0 `navigateTo = null`. The overlay appears on the current page. Auto-navigation only happens on steps 1-7 when the user clicks "下一步"/"上一步".

## Impact

- `MainActivity.kt`: `onRestartTutorial` only sets `showTutorial = true`
- `TutorialOverlay.kt`: Step 0 `navigateTo = null` (no auto-navigation on welcome step)
