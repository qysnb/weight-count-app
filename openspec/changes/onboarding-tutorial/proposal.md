## Why

New users have no guidance on how to use the app — they see an empty record list, chart, and settings without understanding the workflow. An onboarding tutorial reduces the learning curve and improves first-time experience. The Settings page also needs version and developer credit updates for the v2.0.0 release.

## What Changes

1. **Onboarding tutorial**: A multi-step guided tour that introduces new users to the three main modules (记录, 报表, 设置), creates 2 sample weight records, shows the resulting chart, then guides the user to delete the sample data.
2. **Settings — "重新体验教程" option**: A new interactive item that triggers the tutorial again.
3. **About page**: Update version to v2.0.0, update developer credit to "Qysnb with DeepSeek V4 Flash".

## Capabilities

### New Capabilities
- `onboarding-tutorial`: First-run guided tour introducing app modules with sample data creation and cleanup.

### Modified Capabilities
- `user-settings`: Settings page adds "重新体验教程" option; About page updates version (v2.0.0) and developer credit.

## Impact

- New `TutorialScreen.kt` composable with step-by-step dialog/overlay flow
- `SettingsScreen.kt`: add "重新体验教程" row under "其他" section
- `SettingsViewModel.kt`: add tutorial trigger event
- `AboutScreen.kt`: version string → "v2.0.0", developer → "Qysnb with DeepSeek V4 Flash"
- `Navigation.kt` (or equivalent): add tutorial screen route
- `WeightRecordDao.kt` / `WeightRepository.kt`: may need temp sample data insertion/deletion methods
