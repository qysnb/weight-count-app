## Context

The tutorial overlay suffered from a composition-timing bug. Using `if (showTutorial) { TutorialOverlay(...) }` meant the overlay was **dynamically added** to the composition tree each time it was shown. This dynamic addition occurred during a frame where the parent Box was also relaying out due to Navigation or recomposition cascades. The fragile initial composition was disrupted, making the overlay visually "disappear."

**Root cause:** The Compose slot table assigns a fixed position to each composable. When a conditional composable flips from absent to present, the slot allocation and the first composition happen in the same frame. If the parent also recomposes in that same frame (due to Navigation state changes), the slot can become inconsistent, leading to visual artifacts or the composable being skipped entirely.

## Goals / Non-Goals

**Goals:**
- Tutorial overlay always appears reliably on first and subsequent triggers
- Auto-trigger on first launch for new users

**Non-Goals:**
- No changes to TutorialStep data or step descriptions
- No changes to Settings screen

## Decisions

### Decision 1: Always-composed overlay pattern

**Chosen:** Remove `if (showTutorial)` from MainActivity. Always render `TutorialOverlay` in the composition tree. Use `visible: Boolean` parameter to control AnimatedVisibility.

**Rationale:** By always keeping the overlay in the tree, its slot in the composition hierarchy is stable. The `visible` parameter only changes the AnimatedVisibility state — no composition addition/removal occurs. The overlay's remembered state (`currentStep`, `isCollapsed`) persists across visibility changes.

**Structure:**
```kotlin
Box(Modifier.fillMaxSize()) {
    Scaffold { NavGraph(...) }
    TutorialOverlay(
        visible = showTutorial,  // only affects AnimatedVisibility
        onComplete = { ... },
        onNavigate = { ... }
    )
}
```

### Decision 2: State reset on visibility change

**Chosen:** `LaunchedEffect(visible) { if (visible) { currentStep = 0; isCollapsed = false } }`

**Rationale:** When the user restarts the tutorial (from Settings button or auto-trigger), the overlay becomes visible again. The LaunchedEffect resets step and collapse state, ensuring the tutorial starts fresh.

### Decision 3: Auto-trigger via DataStore

**Chosen:** `collectAsState(initial = true) + LaunchedEffect(tutorialCompleted)`

**Rationale:** Using `initial = true` prevents the tutorial from flashing before DataStore loads. Only when DataStore confirms `tutorialCompleted == false` does the auto-trigger fire.

## Risks / Trade-offs

- The overlay is always in the tree, consuming minimal memory. AnimatedVisibility removes content from composition when not visible — no performance impact.
- The expand button (···) when collapsed is OUTSIDE AnimatedVisibility but inside the wrapper Box.
