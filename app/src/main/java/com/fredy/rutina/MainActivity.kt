package com.fredy.rutina

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fredy.rutina.ui.DayDetailScreen
import com.fredy.rutina.ui.LibraryScreen
import com.fredy.rutina.ui.WeekScreen
import com.fredy.rutina.ui.theme.RutinaFredyTheme

class MainActivity : ComponentActivity() {

    private val viewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RutinaFredyTheme {
                RutinaFredyApp(viewModel)
            }
        }
    }
}

@Composable
fun RutinaFredyApp(viewModel: AppViewModel) {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = "semana") {
        composable("semana") {
            WeekScreen(
                viewModel = viewModel,
                onDayClick = { dayId -> navController.navigate("dia/$dayId") },
                onLibraryClick = { navController.navigate("biblioteca") },
                onHistoryClick = { navController.navigate("historial") }
            )
        }
        composable("historial") {
            HistoryScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable("dia/{dayId}") { backStackEntry ->
            val dayId = backStackEntry.arguments?.getString("dayId") ?: return@composable
            DayDetailScreen(
                viewModel = viewModel,
                dayId = dayId,
                onBack = { navController.popBackStack() }
            )
        }
        composable("biblioteca") {
            LibraryScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
