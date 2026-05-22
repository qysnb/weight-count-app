# 体重记录器 (Weight Count App)

一个基于 Android 的体重跟踪应用，支持记录体重数据、自定义标签、图表展示和周期报表。

## 技术栈

- **语言**: Kotlin
- **UI**: Jetpack Compose + Material 3 (Dynamic Color)
- **架构**: MVVM + Repository
- **数据库**: Room (SQLite)
- **数据持久化**: DataStore Preferences
- **图表**: Canvas 自定义绘制

## 功能

- **标签化记录**: 支持为每次体重记录添加自定义标签，如"睡前""饭后"等，便于后续分类分析
- **灵活的数据管理**: 提供完整的数据增删改查操作，每条记录可精确至分钟（例如 16:37），并实现自动时序排序。通过图形化界面，用户可快速定位到任意年、月、日、时下的具体数据
- **智能折线图报表**: 自动生成包含最小值、最大值及平均值的折线图，直观呈现体重波动趋势。用户可与图表进行交互，点选任意日期获取当日详细记录
- **多维度数据筛选**: 在折线图界面中，支持按日期范围与标签类型（如仅查看"睡前"数据）进行动态筛选，帮助用户深入分析特定状态下的体重变化规律
- **设置**: 主题切换、单位切换、标签管理、周期配置、目标体重设置

## 环境要求

- Android 8.0 (API 26) 及以上
- Gradle 8.x
- Android Studio Hedgehog 或更新版本

## 构建

```bash
./gradlew assembleDebug
```

APK 文件位置：`app/build/outputs/apk/debug/app-debug.apk`

## 截图

<!-- 可后续添加截图 -->

## 许可证

MIT license

---

# Weight Count App

An Android weight tracking application supporting weight recording, custom tags, chart reports, and periodic statistics.

## Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose + Material 3 (Dynamic Color)
- **Architecture**: MVVM + Repository
- **Database**: Room (SQLite)
- **Data Persistence**: DataStore Preferences
- **Charts**: Custom Canvas drawing

## Features

- **Tagged Recording**: Add custom tags (e.g., "before bed", "after meal") to each weight record for later categorization and analysis
- **Flexible Data Management**: Full CRUD operations with minute precision (e.g., 16:37) and automatic chronological sorting. Quickly navigate to any year, month, day, or hour through the graphical interface
- **Smart Line Chart Reports**: Auto-generated line charts with min, max, and average values to visualize weight trends. Interact with the chart by tapping any date to view detailed records
- **Multi-dimensional Filtering**: Filter by date range and tag type (e.g., view only "before bed" data) within the chart interface for in-depth analysis of weight patterns under specific conditions
- **Settings**: Theme switching, unit switching, tag management, period configuration, target weight setting

## Requirements

- Android 8.0 (API 26) or higher
- Gradle 8.x
- Android Studio Hedgehog or newer

## Build

```bash
./gradlew assembleDebug
```

APK location: `app/build/outputs/apk/debug/app-debug.apk`

## Screenshots

<!-- Add screenshots here -->

## License

MIT license
