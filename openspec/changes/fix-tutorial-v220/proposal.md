## Why

Tutorial v2.1.3 still has critical usability bugs: the manual trigger from Settings fails on first attempt (step 0 doesn't render), "上一步"/"下一步" navigation doesn't switch tabs, and step 3/6 texts are inaccurate. These issues make the tutorial unreliable and confusing for users.

## What Changes

- Fix manual trigger: ensure first attempt starts tutorial correctly (navigate synchronously before overlay)
- Fix step navigation: "上一步" / "下一步" now navigates to each step's corresponding tab (Record, Report, Settings)
- Step 3 text: "+按钮" position from "右下角" to "下方中间"; "添加一条记录" to "添加两条记录"
- Step 6 text: "使用本教程" to "再次体验本教程"
- Version bump to v2.2.0

## Capabilities

### New Capabilities
*(none)*

### Modified Capabilities
- `tutorial-manual-trigger`: Fix first-attempt trigger failure; ensure step navigation calls `onStepChange` which navigates to the correct tab
- `tutorial-step-texts`: Update step 3 and step 6 descriptions for accuracy

## Impact

- `MainActivity.kt`: `onRestartTutorial` must navigate before setting `showTutorial`
- `TutorialOverlay.kt`: step navigation callbacks must trigger tab navigation; step 3/6 description strings updated
- `AboutScreen.kt`: version string `v2.1.3` → `v2.2.0`
- `app/build.gradle.kts`: versionName `2.1.3` → `2.2.0`
