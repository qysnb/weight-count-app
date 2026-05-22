## Why

The current onboarding tutorial has several issues: it auto-creates sample data instead of guiding users to interact naturally, the navigation sequence doesn't start at the correct tab on entry, and there may be edge cases where the tutorial fails to trigger on first launch. These problems confuse rather than help new users. The About page also needs version bump to v2.0.1.

## What Changes

1. **Tutorial becomes pure guidance**: Remove all sample data auto-creation and deletion logic. Tutorial steps use visual arrow indicators to guide users to tap buttons and input data themselves.
2. **Tutorial starts at 记录 tab**: When tutorial begins, the app immediately navigates to the 记录 tab so the user can see the interface they're learning about.
3. **Tutorial step UI overhaul**: Each step shows a descriptive overlay with a directional arrow pointing to the relevant UI area (bottom tab bar / FAB / etc.), guiding the user to interact.
4. **Fix tutorial trigger**: Ensure the `tutorialCompleted` flag check correctly triggers on first launch.
5. **Simplify TutorialOverlay**: Remove `WeightRepository` and `TagRepository` dependencies since no sample data operations are needed.
6. **About page**: Update version to v2.0.1.

## Capabilities

### New Capabilities
- *(none)*

### Modified Capabilities
- `onboarding-tutorial`: Tutorial changes from auto-sample-creation to pure guidance with visual arrows; navigation starts at 记录 tab.
- `user-settings`: About page version updated to v2.0.1.

## Impact

- `TutorialOverlay.kt` — major rewrite: remove auto-create samples, add arrow indicators, simplify to pure guidance, fix navigation sequence
- `MainActivity.kt` — fix tutorial trigger logic, ensure initial navigation to 记录 tab on tutorial start
- `AppDatabase.kt` — remove `MIGRATION_2_3` and `isSample` column (revert) if no longer needed
- `WeightRecord.kt` — remove `isSample` field if no longer needed
- `WeightDao.kt` — remove `deleteAllSamples()` if no longer needed
- `WeightRepository.kt` — remove `addSampleRecord()` and `deleteAllSamples()` if no longer needed
- `AboutScreen.kt` — version "v2.0.1"
- `app/build.gradle.kts` — versionName "2.0.1"
