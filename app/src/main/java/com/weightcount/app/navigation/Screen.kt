package com.weightcount.app.navigation

sealed class Screen(val route: String) {
    data object Record : Screen("record")
    data object Report : Screen("report")
    data object Settings : Screen("settings")
    data object AddRecord : Screen("record/add")
    data object EditRecord : Screen("record/edit/{recordId}") {
        fun createRoute(recordId: Long) = "record/edit/$recordId"
    }
    data object About : Screen("settings/about")
}
