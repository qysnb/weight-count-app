## Context

The tutorial overlay disappears on first trigger. Root cause: step 0 has `navigateTo = Screen.Record.route`. When TutorialOverlay is composed for the first time, `LaunchedEffect(0)` fires and calls `onNavigate(Record)`. This invokes `navController.navigate(Record)` with `popUpTo(start) + restoreState = true`. The backstack manipulation (popping Settings, restoring Record state) disrupts the overlay's initial composition, making it visually "disappear."

Pre-navigation in `onRestartTutorial` (v2.3.0 attempt) also failed because the second navigation from LaunchedEffect(0) still disrupted the composition.

Second trigger works because the backstack is already stable — Record is at the top, LaunchedEffect's navigation is a true no-op with no backstack manipulation.

## Goals / Non-Goals

**Goals:**
- Tutorial overlay stays visible on first trigger
- User can interact with the tutorial immediately

**Non-Goals:**
- Auto-navigation to Record on step 0 (removed — user clicks "下一步" to navigate)
- No changes to step 1-7 navigation behavior

## Decisions

### Decision 1: Remove all initial navigation

**Chosen:** `onRestartTutorial` only sets `showTutorial = true`. Step 0 `navigateTo = null`.

**Rationale:** No navigation happens during the overlay's initial composition. The overlay appears where the user is (Settings). LaunchedEffect(0) does nothing (navigateTo is null). User clicks "下一步" → step 1 → LaunchedEffect(1) fires → navigates to Record with a fully established overlay.

**Why pre-navigation failed:** Even with `onRestartTutorial` navigating first, step 0's `navigateTo = Record` caused LaunchedEffect(0) to navigate again with `restoreState = true`, which disrupted the overlay.

## Risks / Trade-offs

- **Step 0 shows on Settings page instead of Record** — The welcome message mentions all three modules. Acceptable trade-off for reliable tutorial behavior.
