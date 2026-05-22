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

- **记录**: 日历选择日期，记录体重值，支持 kg/lb 单位
- **标签**: 自定义标签管理，可为每条记录添加多个标签
- **报表**: 7日/30日/90日/180日/年度趋势折线图，支持自定义日期范围
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

Apache License 2.0
