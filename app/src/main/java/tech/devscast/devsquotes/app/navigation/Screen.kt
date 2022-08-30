package tech.devscast.devsquotes.app.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object About : Screen("about")
    object Setting : Screen("setting")
    object Quote : Screen("quote")
}
