## Context

Current state:
- MainActivity has `LaunchedEffect(Unit)` that auto-triggers tutorial when DataStore emits `tutorialCompleted=false` — still has race condition on some devices
- TutorialOverlay uses a full-screen dim overlay (alpha=0.6f) that persists even when collapsed
- Tooltip card is positioned with fixed 24.dp padding from bottom
- Step texts have various issues (arrow symbols, "!", missing delete guidance)
- Chart's `WeightChart` early-returns when `sortedData.size < 2`, drawing nothing for single-point datasets

## Goals / Non-Goals

**Goals:**
- Remove auto-trigger, make tutorial entirely manual (Settings entry)
- Rename "重新体验教程" → "使用教程"
- Reposition tooltip: bottom 10% margin, dynamic top
- Remove dim overlay when tooltip is collapsed
- Fix step content issues (arrows, "!", add delete-data step)
- Chart renders single data point
- Version v2.1.1

**Non-Goals:**
- No changes to tutorial data model or persistence
- No changes to chart interaction beyond single-point rendering
- No changes to navigation architecture

## Decisions

| Decision | Choice | Rationale |
|----------|--------|-----------|
| Auto-trigger removal | Delete entire `LaunchedEffect(Unit)` block from `MainActivity` | Simplest approach; tutorial only starts from Settings |
| Overlay dim removal on collapse | Split overlay into two Box layers: dimmed background + tooltip. Hide dimmed background when `isCollapsed=true` | User wants full screen brightness when tooltip is minimized |
| Bottom margin | `Modifier.padding(bottom = screenHeight * 0.1f)` computed via `BoxWithConstraints` | Percentage-based spacing adapts to any screen size |
| Single-point chart | Remove early return, add branch for `size == 1` that draws just the grid + point | Minimal change, preserves all existing multi-point behavior |

## Risks / Trade-offs

- **No auto-trigger**: New users won't discover tutorial automatically. Mitigation: "使用教程" is prominent in Settings, and the tutorial text still references it.
- **Single-point chart grid**: Axis labels and grid lines may look sparse with one point. Acceptable — user can add more data later.
