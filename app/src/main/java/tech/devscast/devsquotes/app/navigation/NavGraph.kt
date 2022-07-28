package tech.devscast.devsquotes.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import tech.devscast.devsquotes.presentation.screen.home.HomeScreen
import tech.devscast.devsquotes.presentation.screen.splash.SplashScreen

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun MainNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(route = Screen.Setting.route) {
        }
        composable(route = Screen.Favorite.route) {
        }
    }
}
