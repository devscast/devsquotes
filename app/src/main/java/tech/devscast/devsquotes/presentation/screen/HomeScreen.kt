package tech.devscast.devsquotes.presentation.screen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import tech.devscast.devsquotes.app.navigation.Screen
import tech.devscast.devsquotes.presentation.screen.busness.HomeState
import tech.devscast.devsquotes.presentation.screen.busness.HomeViewModel
import tech.devscast.devsquotes.presentation.screen.component.SwipeableCard
import tech.devscast.devsquotes.presentation.screen.component.TopPageBar
import tech.devscast.devsquotes.presentation.theme.FavoriteBotBlack
import tech.devscast.devsquotes.presentation.theme.White

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {

    val quotes by viewModel.data.collectAsState()
    val context = LocalContext.current

    BackHandler(enabled = true) {
        (context as? Activity)?.finish()
    }
    Scaffold(
        topBar = {
            TopPageBar(navController = navController)
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.Favorite.route)},
                backgroundColor = FavoriteBotBlack,
                shape = RoundedCornerShape(10.dp)) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = null, tint = White)
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            if (quotes is HomeState.Success) {
                SwipeableCard((quotes as HomeState.Success).quotes) /*{
                    Toast.makeText(context, it.author, Toast.LENGTH_SHORT).show()
                }*/
            }
        }
    }
}


