## Context

New Android application (Kotlin, Jetpack Compose, Material 3) targeting API 26+. The app is fully offline-local with Room database. The user needs a clean, modern weight tracker with flexible tagging, calendar overview, and configurable chart reports.

## Goals / Non-Goals

**Goals:**
- Define system architecture: MVVM with Repository pattern on Android
- Establish component tree: BottomNavigation → NavHost → per-tab composable screens
- Data model: Room entities for weight records and tags, many-to-many relationship
- Chart rendering: Custom Canvas line chart for weight trends with x-axis deduplication
- Settings persistence: DataStore Preferences for report period names/values

**Non-Goals:**
- No cloud sync, no user accounts, no multi-device
- No nutrition/exercise tracking — weight-only
- No Wear OS or tablet-adaptive layout (phone-first, but responsive)
- No unit other than kg (configurable in future, but out of scope for v1)
- No font size adjustment (removed in v1.1)

## Decisions

1. **Compose Navigation (type-safe) over Fragments**
   - Single-activity architecture with Compose Navigation for tab switching
   - Avoids Fragment lifecycle complexity; aligns with modern Android practice

2. **Room over SQLiteOpenHelper / Realm**
   - Room provides compile-time query verification, Flow-based reactive streams, and seamless Kotlin coroutine integration
   - Two entities: `WeightRecord` (id, weight, timestamp, note) and `Tag` (id, name, isPreset)
   - Cross-reference: `RecordTagCrossRef` for many-to-many

3. **Compose Canvas chart over library dependency**
   - Custom Canvas line chart replaces Vico (removed due to API compatibility issues)
   - Full control over Chinese locale formatting, tooltip layout, x-axis deduplication
   - No third-party charting dependency; smaller APK size

4. **DataStore Preferences over SharedPreferences**
   - Type-safe, coroutine-native, async reads
   - Stores: reportPeriodNames (map of key→name), reportPeriodDays (map of key→days)

5. **Material 3 Dynamic Color over fixed palette**
   - Monet engine picks wallpaper-based accent; provides light/dark themes for free
   - Fallback palette for non-Android 12+ devices

6. **Simplified Chinese (zh-CN) as sole UI locale**
   - All user-facing strings hardcoded in Simplified Chinese — no i18n strings.xml indirection needed for v1
   - Device locale-based date/number formatting resolves to Chinese automatically on zh-CN devices
   - Calendar week starts on Monday to match Chinese convention

## Architecture Diagram (Text)

```
┌────────────────────────────────────────────────┐
│  Compose UI Layer (Screens + Components)       │
│  ┌──────────┐ ┌──────────┐ ┌────────────────┐  │
│  │ Record   │ │ Report   │ │ Settings       │  │
│  │ Screen   │ │ Screen   │ │ Screen         │  │
│  └────┬─────┘ └────┬─────┘ └───────┬────────┘  │
│       │            │               │            │
│  ┌────▼────────────▼───────────────▼────────┐  │
│  │ ViewModels                               │  │
│  │ (RecordViewModel, ReportViewModel,       │  │
│  │  SettingsViewModel)                      │  │
│  └────┬────────────┬───────────────┬────────┘  │
├───────┼────────────┼───────────────┼──────────┤
│  ┌────▼────────────▼───────────────▼────────┐  │
│  │ Repository Layer                         │  │
│  │ (WeightRepository, TagRepository)        │  │
│  └────┬────────────┬───────────────┬────────┘  │
├───────┼────────────┼───────────────┼──────────┤
│  ┌────▼────────────▼───────────────▼────────┐  │
│  │ Data Layer                               │  │
│  │ ┌───────────┐ ┌─────────┐ ┌───────────┐  │  │
│  │ │ Room DB   │ │DataStore│ │  ┌──────┐ │  │  │
│  │ │ ─ DAOs    │ │Prefs    │ │  │Tags  │ │  │  │
│  │ │ ─ Entities│ │         │ │  │(seed)│ │  │  │
│  │ └───────────┘ └─────────┘ └───────────┘  │  │
│  └──────────────────────────────────────────┘  │
└────────────────────────────────────────────────┘
```

## Navigation Graph

```
BottomNavBar (记录 / 报表 / 设置)
├── NavGraph
│   ├── "record"      → RecordScreen      (MonthYearSelector + CalendarHeader + WeightLogList + FAB)
│   ├── "report"      → ReportScreen      (ChartCanvas + PeriodSelectorRow + DateRangeSelector + Stats)
│   └── "settings"    → SettingsScreen    (TagManager + ReportCustomizer + About)
│
│   Dialog routes (overlay):
│   ├── "record/add"  → AddWeightDialog   (WeightInput + TwoLineDateBtn + TimeBtn + Tags + Confirm)
│   ├── "record/edit/{id}" → EditWeightDialog (pre-filled, same layout)
│   └── "settings/tags/add" → AddTagDialog
```

## Data Model

```
WeightRecord
├── id: Long (PK, auto)
├── weight: Double (kg)
├── timestamp: Long (epoch millis)
├── note: String? (optional)
└── tags: List<Tag> (via RecordTagCrossRef)

Tag
├── id: Long (PK, auto)
├── name: String (unique, e.g. "饭前")
└── isPreset: Boolean (true for defaults, cannot delete)

RecordTagCrossRef
├── recordId: Long (FK → WeightRecord)
├── tagId: Long (FK → Tag)
└── (composite PK)
```

## Component Tree (Record Screen Detail)

```
RecordScreen
├── MonthYearSelector
│   └── [<] [yyyy年M月] [>]
├── CalendarHeader
│   └── LazyVerticalGrid (day cells with weight badge)
├── WeightLogList
│   └── WeightLogItem (date, weight, tag chips, edit icon)
└── FAB → "记录" → AddWeightDialog
    ├── TwoLineDateButton (yyyy年 \n M月d日)
    ├── TimeButton (HH:mm, same height as date button)
    ├── WeightTextField (numeric keyboard)
    ├── TagChipGroup (selectable chips)
    └── ConfirmButton
```

## Report Screen Layout

```
┌──────────────────────────────┐
│     Canvas Line Chart        │  ~60%
│   (grid + line + points +    │
│    deduplicated x-labels)    │
│                              │
│   ┌─────── tooltip ────────┐│
│   │ 2026年5月21日          ││
│   │ 08:30  70.5 kg         ││
│   │ 饭前 饭后              ││
│   └─────────────────────────┘│
├──────────────────────────────┤
│ 选择周期 [7日][30日][90日][年度]  │
│ 选择范围                     │
│ [起始日期]  [结束日期]        │
│ ── stats row ──             │
│ 最低:XX  平均:XX  最高:XX    │
└──────────────────────────────┘
```

## Settings Screen Layout

```
┌──────────────────────────────┐
│ 设置                         │
│                              │
│ 自定义标签                    │
│ ┌── tag list ──────────────┐│
│ │ 饭前 (预设)         [删除]││
│ │ 饭后 (预设)         [删除]││
│ │ 睡前 (预设)         [删除]││
│ │             [+ 添加]     ││
│ └──────────────────────────┘│
│                              │
│ 报表自定义                    │
│ ┌── period list ───────────┐│
│ │ 7日 (7天)                ││
│ │ 30日 (30天)              ││
│ │ 90日 (90天)              ││
│ │ 年度 (365天)             ││
│ │ (tap to edit name+days)  ││
│ └──────────────────────────┘│
│                              │
│ 关于                         │
│ → 关于                       │
└──────────────────────────────┘
```

## Risks / Trade-offs

| Risk | Mitigation |
|------|-----------|
| Room DB migration on schema change | Add explicit migration tests; version increment in openHelper callback |
| Canvas chart performance with 365+ data points | Canvas handles up to ~10k points smoothly; batch rendering if needed |
| User accidentally deletes a record | Add confirmation dialog; consider soft-delete in v2 |
| Custom tags cause data inconsistency | Tag name unique constraint enforced at DB level |
| Chinese locale formatting edge cases | Use `java.time` + `DateTimeFormatter.ofLocalizedDate` with device locale |
