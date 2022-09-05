package tech.devscast.devsquotes.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import kotlinx.coroutines.ExperimentalCoroutinesApi
import tech.devscast.devsquotes.presentation.screen.favorites.FavoriteScreen
import tech.devscast.devsquotes.presentation.screen.showquote.ShowQuoteScreen
import tech.devscast.devsquotes.presentation.screen.splash.SplashScreen

@Composable
fun MainNavGraph(navController: NavHostController) {

    val uri = "https://quotes.devscast.tech"

    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(route = Screen.Splash.route) {
            SplashScreen {
                navController.navigate(Screen.Quote.route)
            }
        }

        composable(route = Screen.Favorite.route) {
            FavoriteScreen(navController)
        }

        composable(
            route = "${Screen.Quote.route}?id={id}",
            deepLinks = listOf(navDeepLink { uriPattern = "$uri?id={id}" }),
            arguments = listOf(navArgument("id") { nullable = true })
        ) { backStackEntry ->
            ShowQuoteScreen(
                navController = navController,
                quoteId = backStackEntry.arguments?.getString("id") ?: ""
            )
        }
    }
}
