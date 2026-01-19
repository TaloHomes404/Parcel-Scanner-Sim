package wolf.north.parcelscannerapp.navigation

sealed class Screen(val route: String) {

    object Home: Screen("home")
    object History: Screen("history")
    object Profile: Screen("profile")


}