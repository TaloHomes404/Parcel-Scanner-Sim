package wolf.north.parcelscannerapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import wolf.north.parcelscannerapp.mvvm.View.History.HistoryScreen
import wolf.north.parcelscannerapp.mvvm.View.Home.HomeScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route // Start from Home Screen
    ) {
        // Home
        composable(Screen.Home.route) {
            HomeScreen(
                onGoToHistory = {
                    navController.navigate(Screen.History.route)
                }
            )
        }

        // History
        composable(Screen.History.route) {
            HistoryScreen(
                onGoToHome = {
                    navController.navigate(Screen.Home.route)
                }
            )
        }

        // Profile
        composable(Screen.Profile.route) {

        }
    }
}