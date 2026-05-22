## Why

Tracking body weight over time is a fundamental health habit, but existing mobile solutions are either over-engineered with nutrition/exercise tracking or lack flexible tagging and customizable reporting. This app fills the gap: a dedicated, lightweight weight tracker with native Android UX, custom tags, and intuitive charts — designed for users who want a focused, ad-free experience.

## What Changes

This project introduces a new Android application from scratch:

- **Weight Recording**: Log weight (kg/lbs) with precise timestamp and optional tags via a modal dialog
- **Tag System**: Predefined tags (饭前/饭后/睡前) with full user-defined CRUD
- **Edit Records**: Inline editing of weight, tags, and time by tapping a calendar entry
- **Reports & Charts**: Line chart (MPAndroidChart or similar) showing weight trends over 7/30/90/365-day windows
- **Bottom Navigation**: Three-tab layout (记录 / 报表 / 设置) occupying bottom 8% of screen
- **Settings Screen**: Custom tag management, font size, report date range presets, about page
- **Persistence**: Local SQLite/Room database
- **Modern UI**: Material 3 / Material You design with dynamic colors

## Capabilities

### New Capabilities

- `weight-recording`: Core weight log entry with date/time picker, weight input, and tag selection dialog
- `tag-management`: Create, read, update, delete custom tags; default seed tags (饭前, 饭后, 睡前)
- `data-reporting`: Line chart rendering with 7/30/90/365-day presets; calendar heat-map view
- `user-settings`: Font size, report customization, tag editor, about page with licenses
- `ui-navigation`: Bottom navigation scaffold with Record, Report, and Settings tabs

### Modified Capabilities

*None — new project, no existing capabilities.*

## Impact

- **New project**: Android (Kotlin + Jetpack Compose), no migration needed
- **Dependencies**: Room (DB), MPAndroidChart or Vico (charting), Material 3
- **Data path**: `Room DB → Repository → ViewModel → Compose UI`
- **No network/cloud dependency** — fully offline, local-first
