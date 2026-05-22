## Context

The tutorial overlay system has a race condition between navigation and overlay visibility. When the user triggers the tutorial from Settings, `onRestartTutorial` navigates to Record then sets `showTutorial = true`. But `navController.navigate()` is asynchronous — the page switch hasn't completed when the overlay appears. The overlay's `LaunchedEffect(currentStep)` checks step 0's `navigateTo` which was `null`, so it doesn't navigate again. The result: the welcome step appears on the Settings page momentarily before switching to Record.

Additionally, steps 1, 2, and 4 had `navigateTo = null`, meaning LaunchedEffect didn't navigate when entering those steps, leaving users on the wrong tab.

## Goals / Non-Goals

**Goals:**
- Eliminate race condition between navigation and overlay on first trigger
- Ensure every step navigates to the correct tab when entered
- Update step 3 and step 6 texts for accuracy
- Version bump to v2.2.0

**Non-Goals:**
- No architecture changes to the tutorial system
- No new UI components
- No changes to collapse/expand behavior

## Decisions

### Decision 1: Remove pre-navigation from `onRestartTutorial`

**Chosen:** Remove `navController.navigate(Screen.Record.route)` from `onRestartTutorial`. Only set `showTutorial = true`.

**Rationale:** The LaunchedEffect in TutorialOverlay is the single source of truth for navigation. By removing pre-navigation, we eliminate the async race condition entirely. The overlay always handles navigation after it is fully composed.

**Alternative considered:** Using a delay or callback after navigation completes — complex, fragile, and Compose Navigation doesn't provide a reliable completion callback.

### Decision 2: Set step 0 navigateTo to Record

**Chosen:** Step 0 (`navigateTo = Screen.Record.route`) triggers LaunchedEffect(0) which navigates to Record before the user sees anything.

**Rationale:** When TutorialOverlay first appears, currentStep = 0. LaunchedEffect(0) uses `steps[0].navigateTo` to call `onNavigate(Screen.Record.route)`. This navigation runs in a coroutine that's launched after the first composition, guaranteeing the overlay is visible first, then navigation happens.

### Decision 3: Add navigateTo for steps 1, 2, and 4

**Chosen:** 
- Step 1: `navigateTo = Screen.Record.route`
- Step 2: `navigateTo = Screen.Record.route`
- Step 4: `navigateTo = Screen.Report.route`

**Rationale:** LaunchedEffect(currentStep) responds to step changes triggered by "上一步"/"下一步" buttons. Without these values, navigating to a step doesn't switch tabs.

## Risks / Trade-offs

- **Risk:** Navigating to the same page twice (e.g., step 0→Record, step 1→Record) — **Mitigation:** Compose Navigation's `launchSingleTop = true` and `restoreState = true` make re-navigation a no-op.
- **Risk:** Step 0's navigation might still race with initial composition — **Mitigation:** LaunchedEffect is a coroutine that suspends until composition is committed. It's guaranteed to run after the first frame, so the overlay is visible before navigation.
