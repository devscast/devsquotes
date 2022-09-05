package tech.devscast.devsquotes.app.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Favorite : Screen("favorite")
    object About : Screen("about")
    object Setting : Screen("setting")
    object Quote : Screen("quote")
}
