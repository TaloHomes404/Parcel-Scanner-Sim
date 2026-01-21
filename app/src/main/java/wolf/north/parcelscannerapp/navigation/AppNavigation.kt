package wolf.north.parcelscannerapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import wolf.north.parcelscannerapp.mvvm.View.History.HistoryScreenContent
import wolf.north.parcelscannerapp.mvvm.View.Home.HomeScreenContent
import wolf.north.parcelscannerapp.ui.AppScaffold

@Composable
fun AppNavigation(navController: NavHostController) {

    //**
    //Navigation scaffold vals to observe active screen by nav
    //**
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    AppScaffold(
        title = when (currentRoute) {
            Screen.Home.route -> "Parcel Scanner"
            Screen.History.route -> "Scan History"
            Screen.Profile.route -> "Worker Profile"
            else -> "Parcel Scanner"
        },
        currentRoute = currentRoute,
        navController = navController
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route // Start from Home Screen
        ) {
            // Home content - loaded into composable scaffold
            composable(Screen.Home.route) {
                HomeScreenContent()
            }

            // History content - loaded into composable scaffold
            composable(Screen.History.route) {
                HistoryScreenContent()
            }

            // Profile content - loaded into composable scaffold
            composable(Screen.Profile.route) {
                //ProfileScreenContent()
            }
        }
    }
}