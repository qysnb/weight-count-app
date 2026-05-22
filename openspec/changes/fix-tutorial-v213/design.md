## Context

Current state after v2.1.2:
- `onRestartTutorial` = `showTutorial = true` only — relies on TutorialOverlay's LaunchedEffect to navigate. The LaunchedEffect is async (runs after composition commit), so on the first frame the overlay appears before navigation completes, causing a flash/abort.
- `AnimatedVisibility(Modifier.fillMaxSize())` wraps the Card — but `align(BottomCenter)` inside AnimatedVisibility doesn't reliably work because AnimatedVisibility's layout scope doesn't fully delegate BoxScope alignment.
- Step 2 has `← 上月 → 下月` but user wants arrows directly attached with no space after them.

## Goals / Non-Goals

**Goals:**
- Settings trigger navigates first, then shows overlay — no race, no flash
- Card reliably bottom-aligned with 10% margin
- Dim overlay reliably hidden when collapsed
- Step 2: arrows directly attached for month, `< ` and `> ` with space for year
- Version v2.1.3

**Non-Goals:**
- No changes to step structure, chart, or data layer

## Decisions

| Decision | Choice | Rationale |
|----------|--------|-----------|
| Trigger sequencing | Navigate FIRST (`navController.navigate`), THEN `showTutorial = true` | Navigation is a state change that triggers composition; setting showTutorial after ensures the target tab is rendered before the overlay measures itself |
| Step 0 nav removal | `navigateTo = null` for welcome step | MainActivity already handled navigation; LaunchedEffect would double-navigate |
| AnimatedVisibility layout | Wrap Card in `Box(Modifier.fillMaxSize())` | Nested Box provides a reliable `BoxScope` for `align(BottomCenter)` — eliminates ambiguity about AnimatedVisibility's layout delegation |
| Collapse brightness | No code change — dim overlay already gated by `if (!isCollapsed)` | Fixing the Card layout (which may have been causing partial visibility) should resolve the perceived brightness issue |

## Risks / Trade-offs

- **Navigation before show**: If `navController.navigate` throws (e.g., destination not found), `showTutorial` never becomes true — tutorial won't appear. Mitigation: route is hardcoded `Screen.Record.route` which always exists.
- **Box wrapper in AnimatedVisibility**: Adds one extra layout node — negligible performance impact.
