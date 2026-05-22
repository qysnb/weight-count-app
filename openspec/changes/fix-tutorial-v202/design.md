## Context

Current tutorial overlay (`TutorialOverlay.kt`) is a full-screen dim overlay with a bottom card. It has 5 steps covering the three main tabs but suffers from:

1. **Race condition in MainActivity**: `LaunchedEffect(tutorialCompleted)` fires when `tutorialCompleted` first emits (initial=null → false). The navController may not be ready, and navigation + `showTutorial` flag don't coordinate with recomposition timing, causing a flash.

2. **Overlay blocks interaction**: The bottom card covers the FAB (+ button) on the 记录 page, and the dim overlay prevents tapping any UI beneath it. Users must dismiss the tutorial to interact.

3. **Tab navigation timing**: Navigation to the next tab happens in the "下一步" button handler (same recomposition frame as advancing the step), so users see the new tab label but not the tab content before the step transitions.

4. **Incomplete coverage**: Calendar year/month arrows and chart data point selection are not explained.

## Goals / Non-Goals

**Goals:**
- Fix tutorial flash/disappear race condition
- Add collapse/expand toggle to overlay tooltip
- Add steps for calendar navigation (record page)
- Add steps for chart data point selection (report page)
- Navigate to target tab *before* showing the step text
- Version bump to v2.0.2

**Non-Goals:**
- No new sample data creation
- No architectural changes to data layer
- No new persistence (tutorial collapse state is in-memory only)

## Decisions

| Decision | Choice | Rationale |
|----------|--------|-----------|
| Flash fix | Use `snapshotFlow` + `LaunchedEffect` keyed on flag, not initial emit | `tutorialCompleted` emits null first, then false — we only react when `false` is first observed |
| Collapse mechanism | `mutableStateOf(false)` + `AnimatedVisibility` for the tooltip card | Simple state toggle; no re-flow of content needed |
| Collapse button | Top-left corner `_` icon on the card, restore via floating button icon at bottom-right | Intuitive position — top-left of card for minimize, bottom-right of screen for restore |
| Navigation timing | `LaunchedEffect(currentStep)` navigates on step change, not button click | Step data declares `navigateTo`; when step index changes, navigation fires immediately |
| Calendar nav steps | Insert as step 1.5 (between welcome and add-record) | Logical order: introduce the page layout before interaction |
| Chart nav steps | Insert as step 2.5 (between report-intro and settings) | After introducing the chart, show interaction details |

## Risks / Trade-offs

- **Collapse button on overlay**: If multiple steps have collapse, state persists per session only — OK since tutorial is a one-time flow.
- **LaunchedEffect navigation**: Rapid step advancement could cause multiple nav calls. Mitigation: `launchSingleTop = true` on all nav calls.
