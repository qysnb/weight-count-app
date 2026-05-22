## Context

Current state:
- `MainActivity.onRestartTutorial` writes DataStore + navigates to Record route BEFORE showing tutorial. TutorialOverlay's `LaunchedEffect(0)` then navigates again to Record route — double navigation causes timing conflict.
- `AnimatedVisibility` wrapping the tooltip Card lacks `fillMaxSize()`, so the Card's `align(BottomCenter)` is a no-op — Card renders at screen top, not bottom.
- Step 2 uses `<` for both month and year. Month should use ← →, year should use `<` `>`.
- Step 7 says "长按或点击" but should just say "点击".

## Goals / Non-Goals

**Goals:**
- Manual Settings trigger works on first attempt
- Tooltip card anchored at bottom with 10% screen-height margin
- Dim overlay disappears when tooltip collapsed
- Correct step 2 and step 7 texts
- Version v2.1.2

**Non-Goals:**
- No changes to step count or step structure
- No changes to chart or data layer

## Decisions

| Decision | Choice | Rationale |
|----------|--------|-----------|
| Trigger fix | Remove DataStore write + nav from `onRestartTutorial` | TutorialOverlay already handles navigation via LaunchedEffect; DataStore no longer consumed anywhere |
| AnimatedVisibility fix | Add `Modifier.fillMaxSize()` to AnimatedVisibility | Allows Card's `align(BottomCenter)` to actually position at screen bottom |
| Text fixes | Direct string edits in step data | Minimal change, no logic impact |

## Risks / Trade-offs

- **Removing DataStore write from trigger**: `tutorialCompleted` flag stays at its current value (no longer reset to false). Since no code reads it anymore (auto-trigger removed), this has no functional impact.
- **fillMaxSize + AnimatedVisibility**: Animation (slideInVertically) will animate across full screen height rather than just card height. Acceptable — still visually smooth.
