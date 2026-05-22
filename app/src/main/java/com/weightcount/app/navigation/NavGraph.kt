package com.weightcount.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.weightcount.app.ui.record.RecordScreen
import com.weightcount.app.ui.report.ReportScreen
import com.weightcount.app.ui.settings.AboutScreen
import com.weightcount.app.ui.settings.SettingsScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    onNavigateToAbout: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Record.route,
        modifier = modifier
    ) {
        composable(Screen.Record.route) {
            RecordScreen()
        }

        composable(Screen.Report.route) {
            ReportScreen()
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateToAbout = onNavigateToAbout
            )
        }

        composable(Screen.About.route) {
            AboutScreen(onBack = { navController.popBackStack() })
        }
    }
}
