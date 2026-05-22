package com.weightcount.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.weightcount.app.navigation.BottomNavBar
import com.weightcount.app.navigation.NavGraph
import com.weightcount.app.navigation.Screen
import com.weightcount.app.ui.theme.WeightCountTheme
import com.weightcount.app.ui.tutorial.TutorialOverlay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeightCountTheme {
                MainScreen()
            }
        }
    }
}

@Composable
private fun MainScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val app = context.applicationContext as WeightCountApp
    val scope = rememberCoroutineScope()

    var showTutorial by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomNavBar(navController = navController)
            }
        ) { innerPadding ->
            NavGraph(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
                onNavigateToAbout = {
                    navController.navigate(Screen.About.route)
                },
                onRestartTutorial = {
                    showTutorial = true
                }
            )
        }

        TutorialOverlay(
            visible = showTutorial,
            onComplete = {
                scope.launch {
                    app.settingsDataStore.setTutorialCompleted(true)
                    showTutorial = false
                }
            },
            onNavigate = { route ->
                navController.navigate(route) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}
