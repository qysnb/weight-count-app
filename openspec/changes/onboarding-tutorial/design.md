## Context

The app has three main tabs: 记录 (recording), 报表 (report/chart), 设置 (settings). New users currently see empty states on first launch with no guidance. The Settings page has an "其他" section with just "关于". The About page shows v1.7.0 and "开发者: Qyforest".

## Goals / Non-Goals

**Goals:**
- First-launch onboarding tutorial with 4-6 steps introducing each module
- Tutorial creates 2 sample weight records and shows chart effect, then guides cleanup
- "重新体验教程" option in Settings → "其他" section
- About page: v2.0.0, "Qysnb with DeepSeek V4 Flash"

**Non-Goals:**
- No video/animation tutorial (text + arrows only)
- No forced tutorial (user can dismiss)
- No persistent tutorial state tracking beyond a DataStore boolean

## Decisions

- **Overlay dialog approach**: Use a semi-transparent overlay with text descriptions and highlight areas, rather than a separate full-screen activity. This keeps users in context and shows actual UI elements.
- **Sample data as real records**: Insert sample records directly into Room with a special `isSample: Boolean` column (or tag-based marker) so they participate in chart rendering naturally and can be batch-deleted after tutorial.
- **DataStore boolean `tutorial_completed`**: Simple boolean flag on first launch. Resetting it ("重新体验教程") allows replay.
- **No new navigation route**: Tutorial is an overlay composable shown conditionally, not a new destination. This avoids back-stack complexity.

## Risks / Trade-offs

- [Sample data cleanup] → Provide a single "delete all sample data" button at the tutorial's last step to avoid users manually finding records.
- [Tutorial interruption] → Allow the user to dismiss the tutorial at any step with a "跳过" button.
- [isSample column migration] → Requires Room migration to add `isSample` to `WeightRecord` entity and bump DB version.
