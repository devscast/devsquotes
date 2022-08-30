package tech.devscast.devsquotes.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import kotlinx.coroutines.ExperimentalCoroutinesApi
import tech.devscast.devsquotes.presentation.screen.favorites.FavoriteScreen
import tech.devscast.devsquotes.presentation.screen.showquote.ShowQuoteScreen

@Composable
fun MainNavGraph(navController: NavHostController) {

    val uri = "https://quotes.devscast.tech"

    NavHost(navController = navController, startDestination = "${Screen.Quote.route}/{id}") {
        composable(route = Screen.Setting.route) {
        }
        composable(route = Screen.Favorite.route) {
            FavoriteScreen(navController)
        }

        composable(
            route = "${Screen.Quote.route}/{id}",
            deepLinks = listOf(navDeepLink { uriPattern = "$uri/{id}" }),
        ) { backStackEntry ->
            ShowQuoteScreen(
                navController = navController,
                quoteId = backStackEntry.arguments?.getString("id") ?: ""
            )
        }
    }
}
