package wolf.north.parcelscannerapp.navigation

import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import wolf.north.parcelscannerapp.mvvm.view.history.HistoryScreenContent
import wolf.north.parcelscannerapp.mvvm.view.home.HomeScreenContent
import wolf.north.parcelscannerapp.mvvm.view.login.LoginScreen
import wolf.north.parcelscannerapp.mvvm.view.splashscreen.SplashScreen
import wolf.north.parcelscannerapp.mvvm.viewmodel.LoginViewModel
import wolf.north.parcelscannerapp.ui.AppScaffold

@Composable
fun AppNavigation(navController: NavHostController, nfcIntent: Intent?) {

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
        showBars = currentRoute != Screen.Login.route && !hideBars,
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Login.route, // Start from Login Screen
            modifier = Modifier.padding(paddingValues)

        ) {

            composable(Screen.Login.route) {
                val viewModel: LoginViewModel = viewModel()

                // Coroutine for successfully login state
                LaunchedEffect(viewModel.loginSuccess) {
                    if (viewModel.loginSuccess.value) {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                }

                LoginScreen(
                    nfcIntent = nfcIntent,
                    viewModel = viewModel
                )
            }


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