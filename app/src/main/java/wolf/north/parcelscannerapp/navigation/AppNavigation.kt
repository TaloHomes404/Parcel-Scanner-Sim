package wolf.north.parcelscannerapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import wolf.north.parcelscannerapp.mvvm.view.History.HistoryScreenContent
import wolf.north.parcelscannerapp.mvvm.view.Home.HomeScreenContent
import wolf.north.parcelscannerapp.ui.AppScaffold

@Composable
fun AppNavigation(navController: NavHostController) {

    //**
    //Navigation scaffold vals to observe active screen by nav
    //**
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var hideBars by remember { mutableStateOf(false)
    }

    AppScaffold(
        title = when (currentRoute) {
            Screen.Home.route -> "Parcel Scanner"
            Screen.History.route -> "Scan History"
            Screen.Profile.route -> "Worker Profile"
            else -> "Parcel Scanner"
        },
        currentRoute = currentRoute,
        navController = navController,
        showBars = !hideBars
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route, // Start from Home Screen
            modifier = Modifier.padding(paddingValues)

        ) {
            // Home content - loaded into composable scaffold
            composable(Screen.Home.route) {
                HomeScreenContent(onScannerVisibilityChanged = {hideBars = it})
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