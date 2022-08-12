package tech.devscast.devsquotes.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import tech.devscast.devsquotes.presentation.screen.HomeScreen
import tech.devscast.devsquotes.presentation.screen.favorites.FavoriteScreen

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun MainNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(route = Screen.Setting.route) {
        }
        composable(route = Screen.Favorite.route) {
            FavoriteScreen(navController)
        }
    }
}
