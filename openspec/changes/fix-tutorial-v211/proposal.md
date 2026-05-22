## Why

The tutorial auto-trigger still has race-condition issues for new users. A more robust approach is to remove the auto-trigger entirely and let users start the tutorial manually from Settings. Additional tutorial bugs (overlay position, collapse brightness, step content errors, missing delete-data step) and a chart single-point rendering gap need fixing.

## What Changes

1. **Remove auto-trigger tutorial**: Delete `LaunchedEffect(Unit)` in `MainActivity.kt` — tutorial only shown via Settings. Rename "重新体验教程" → "使用教程" in `SettingsScreen.kt` and update tutorial step descriptions accordingly.
2. **Overlay position**: Tooltip card bottom margin = 10% of screen, top margin dynamically calculated from remaining space.
3. **Collapse brightness**: When tooltip is collapsed, remove the dim overlay (normal brightness), keep only the expand button.
4. **Calendar nav text**: Step 2 — change arrow symbols to `<` and `>` for year navigation.
5. **Remove exclamation mark**: Step 3 — remove "！" from description.
6. **Add delete-data step**: New step between step 5 (设置与自定义) and step 6 (教程完成) guiding users to delete a record.
7. **Single-point chart**: Fix `WeightChart` early-return guard `if (sortedData.size < 2) return@Canvas` to draw a single data point correctly.
8. **Version bump**: About screen → `v2.1.1`, build.gradle.kts → `2.1.1`.

## Capabilities

### New Capabilities
- `manual-tutorial-trigger`: Tutorial only accessible from Settings; no auto-trigger on first launch
- `tutorial-overlay-position`: Tooltip positioned with bottom 10% margin, dynamic top
- `tutorial-collapse-brightness`: Normal screen brightness when tooltip is collapsed (no dim overlay)
- `tutorial-delete-step`: New tutorial step for deleting existing data
- `single-point-chart`: Canvas chart renders single data point correctly

### Modified Capabilities
- (no existing specs to modify)

## Impact

- `MainActivity.kt`: Remove entire `LaunchedEffect(Unit)` block, remove `filter`/`first` imports
- `TutorialOverlay.kt`: Restructure overlay (remove dim on collapse), reposition card, add delete-data step, fix step texts
- `SettingsScreen.kt`: Rename "重新体验教程" → "使用教程"
- `ReportScreen.kt`: Fix `WeightChart` early return guard for single data point
- `AboutScreen.kt`: `v2.0.2` → `v2.1.1`
- `build.gradle.kts`: `2.0.2` → `2.1.1`
