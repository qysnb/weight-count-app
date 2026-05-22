## Why

The v2.0.1 tutorial rewrite fixed the sample data issue but introduced several UX bugs: the tutorial flashes and disappears for new users, overlay tooltips block the very UI elements they describe, tab navigation fires after (not before) the step transition, and key features (calendar navigation, chart data point selection) are not covered.

## What Changes

1. Fix tutorial trigger race condition — new user sees tutorial then it vanishes in ~0.1s
2. Add collapse/expand button on tutorial overlay tooltip — user can shrink the hint to see the UI element behind it, then restore it
3. Add tutorial steps for calendar arrow navigation in 记录 page
4. Add tutorial steps for chart data point selection + arrow switching in 报表 page
5. Fix tab navigation order — navigate to the target tab *before* showing the step, not on "下一步" click
6. Bump version to v2.0.2 in About

## Capabilities

### New Capabilities
- `tutorial-overlay-collapse`: Collapsible tutorial tooltip with expand/contract toggle
- `tutorial-calendar-nav`: Tutorial steps for calendar year/month navigation
- `tutorial-chart-nav`: Tutorial steps for chart data point selection

### Modified Capabilities
- (no existing specs to modify)

## Impact

- `TutorialOverlay.kt`: Major — add collapse state, restructure tooltip layout
- `MainActivity.kt`: Minor — fix LaunchedEffect trigger timing
- `AboutScreen.kt`: Minor — version bump
- `build.gradle.kts`: Minor — versionName bump
