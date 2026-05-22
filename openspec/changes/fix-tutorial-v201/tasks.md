## 1. Remove Sample Data Infrastructure

- [x] 1.1 Removed `isSample` field from `WeightRecord`
- [x] 1.2 Removed `MIGRATION_2_3` and `deleteAllSamples()` from `WeightDao`
- [x] 1.3 Removed `addSampleRecord()` and `deleteAllSamples()` from `WeightRepository`
- [x] 1.4 Reverted DB version to 2, added `fallbackToDestructiveMigration()`

## 2. Rewrite TutorialOverlay — Pure Guidance

- [x] 2.1 Removed `WeightRepository` and `TagRepository` parameters
- [x] 2.2 Rewritten to 5 steps: welcome→record FAB guide→report→settings→completion
- [x] 2.3 Removed all sample creation/deletion coroutine logic
- [x] 2.4 Step 0 navigates to 记录 tab immediately on tutorial start
- [x] 2.5 Cleaned up unused imports

## 3. Fix MainScreen Tutorial Trigger

- [x] 3.1 LaunchedEffect navigates to 记录 tab when tutorial starts
- [x] 3.2 onComplete simplified

## 4. Update About Page and Build Config

- [x] 4.1 AboutScreen version → "v2.0.1"
- [x] 4.2 build.gradle.kts versionName → "2.0.1"
