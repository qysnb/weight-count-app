## 1. Data Layer — Sample Record Support

- [x] 1.1 Added `isSample: Boolean = false` field to `WeightRecord` entity
- [x] 1.2 Bumped DB version 2→3, added `MIGRATION_2_3` for `isSample` column
- [x] 1.3 Added `deleteAllSamples()` to `WeightDao`
- [x] 1.4 Added `addSampleRecord()` and `deleteAllSamples()` to `WeightRepository`

## 2. DataStore — Tutorial Completion Flag

- [x] 2.1 Added `tutorialCompleted` boolean key + getter/setter to `SettingsDataStore`

## 3. Tutorial Overlay Composable

- [x] 3.1 Created `TutorialOverlay.kt`: 6-step full-screen overlay with card, text, skip/prev/next
- [x] 3.2 Step 1: intro with 3 module descriptions
- [x] 3.3 Step 2: auto-creates sample record 1 (70.0kg, "晨起", today 08:00)
- [x] 3.4 Step 3: auto-creates sample record 2 (70.5kg, "睡前", today 22:00)
- [x] 3.5 Step 4: navigates to 报表 tab, explains chart/filters
- [x] 3.6 Step 5: navigates to 设置 tab, explains tags/customization
- [x] 3.7 Step 6: cleanup dialog with "完成教程" → deletes samples

## 4. Navigation — Tutorial Trigger

- [x] 4.1 `MainScreen` collects `tutorialCompleted` flag, shows overlay on first launch
- [x] 4.2 `onComplete` sets `tutorialCompleted = true` in DataStore

## 5. Settings — "重新体验教程"

- [x] 5.1 Added "重新体验教程" row in SettingsScreen under "其他", above "关于"
- [x] 5.2 Wired through NavGraph → MainScreen → sets `tutorialCompleted = false` + shows overlay

## 6. About Page Updates

- [x] 6.1 Version `"v1.7.0"` → `"v2.0.0"`
- [x] 6.2 Developer `"Qyforest"` → `"Qysnb with DeepSeek V4 Flash"`
