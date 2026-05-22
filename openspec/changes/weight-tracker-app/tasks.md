## 1. Project Scaffold & Build Setup

- [x] 1.1 Create Android project with Kotlin, Jetpack Compose, Material 3, API 26+ min
- [x] 1.2 Add all Gradle dependencies: Room, DataStore, Navigation Compose, Material 3
- [x] 1.3 Create package structure: `data/`, `domain/`, `ui/`, `navigation/`, `di/`
- [x] 1.4 Set zh-CN as default locale; configure calendar to Monday start

## 2. Data Layer — Room Database

- [x] 2.1 Define `WeightRecord` entity with id, weight, timestamp, note
- [x] 2.2 Define `Tag` entity with id, name, isPreset
- [x] 2.3 Define `RecordTagCrossRef` for many-to-many relationship
- [x] 2.4 Create `WeightDao` with insert/update/delete/query-by-date-range operations returning Flow
- [x] 2.5 Create `TagDao` with insert/update/delete/selectAll operations returning Flow
- [x] 2.6 Create `AppDatabase` with Room database builder, entities, version, and callback for seed tags
- [x] 2.7 Implement `WeightRepository` wrapping WeightDao with coroutine methods
- [x] 2.8 Implement `TagRepository` wrapping TagDao with coroutine methods
- [x] 2.9 Set up DataStore Preferences for report period names, report period days

## 3. Navigation Shell

- [x] 3.1 Create `NavGraph` composable with routes: record, report, settings
- [x] 3.2 Create `BottomNavBar` composable with 3 tabs (记录/报表/设置) occupying bottom 8% screen
- [x] 3.3 Create `MainActivity` with single-activity architecture, setContent to Scaffold + BottomNavBar + NavHost
- [x] 3.4 Configure Material 3 theme with dynamic color support; set Compose locale to zh-CN

## 4. Record Screen — Weight Logging

- [x] 4.1 Build `RecordScreen` composable with MonthYearSelector, CalendarHeader at top, WeightLogList below, FAB
- [x] 4.2 Build `MonthYearSelector` composable with < > navigation buttons and current month/year label
- [x] 4.3 Build `CalendarHeader` composable using LazyVerticalGrid with weight badge per day
- [x] 4.4 Build `WeightLogList` composable with LazyColumn of WeightLogItem rows
- [x] 4.5 Build `WeightLogItem` composable showing date, weight, tag chips, edit icon
- [x] 4.6 Build `AddWeightDialog` composable with two-line date button, time button, WeightTextField, TagChipGroup, Confirm; reduced transparency (Material 3 default)
- [x] 4.7 Build `EditWeightDialog` composable (reuse AddWeightDialog layout, pre-filled with record data)
- [x] 4.8 Implement `RecordViewModel` with state management for calendar data, log list, add/edit operations, month/year navigation
- [x] 4.9 Implement validation: weight range (20–500), empty field error
- [x] 4.10 Implement delete flow with confirmation dialog from edit dialog

## 5. Tag Management

- [x] 5.1 Build `TagChipGroup` composable showing all tags as selectable chips
- [x] 5.2 Seed 3 default tags (饭前, 饭后, 睡前) on first Room database creation
- [x] 5.3 Implement tag add/rename/delete in Settings screen with dialogs and confirmation
- [x] 5.4 Enforce tag name unique constraint at DB level
- [x] 5.5 Cascade delete RecordTagCrossRef when a tag is deleted

## 6. Report Screen — Weight Charts

- [x] 6.1 Build `ReportScreen` composable with Canvas chart area (top ~60%), period+range selector, stats
- [x] 6.2 Implement Canvas line chart with grid, line, data point circles, tap tooltip
- [x] 6.3 Implement multi-line tooltip: date / time+weight / tags (max 2 per line)
- [x] 6.4 Deduplicate x-axis labels per day — only one label per date
- [x] 6.5 Build period selector row: "选择周期" label + 4 chips in one horizontal line
- [x] 6.6 Build date range selector below period row: "选择范围" + start/end date buttons
- [x] 6.7 Implement `ReportViewModel` that filters records by period or date range
- [x] 6.8 Handle empty-state: show "暂无数据" centered when no records in selected period

## 7. Settings Screen

- [x] 7.1 Build `SettingsScreen` composable with list of options: 自定义标签, 报表自定义, 关于
- [x] 7.2 Build tag management UI within Settings: list all tags with delete button, add button, rename on tap
- [x] 7.3 Remove font size slider and font scale DataStore persistence
- [x] 7.4 Build report customization screen: show period list with name+days, tap to edit both fields
- [x] 7.5 Build About screen with app name, version, developer info, and open-source licenses link

## 8. Polish & Verification

- [x] 8.1 Add confirmation dialogs for all destructive actions (delete record, delete tag)
- [x] 8.2 Ensure tab state preservation when switching between Record / Report / Settings
- [ ] 8.3 Build release APK and verify on API 26+ emulator/device
