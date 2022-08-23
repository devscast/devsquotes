package tech.devscast.devsquotes.presentation.screen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import tech.devscast.devsquotes.R
import tech.devscast.devsquotes.app.navigation.Screen
import tech.devscast.devsquotes.presentation.screen.home.busness.HomeState
import tech.devscast.devsquotes.presentation.screen.home.busness.HomeViewModel
import tech.devscast.devsquotes.presentation.screen.home.component.SwipeableCard
import tech.devscast.devsquotes.presentation.screen.home.component.TopPageBar
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
            TopPageBar(
                title = stringResource(id = R.string.app_name),
                subtitle = stringResource(id = R.string.home_sub_title),
                actionMenu = {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "settings",
                        modifier = Modifier
                            .clickable(
                                enabled = true,
                                onClick = { navController.navigate(Screen.Setting.route) },
                                interactionSource = MutableInteractionSource(),
                                indication = null
                            )
                            .size(30.dp)
                    )
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.Favorite.route) },
                backgroundColor = FavoriteBotBlack,
                shape = RoundedCornerShape(10.dp)
            ) {
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
                SwipeableCard(
                    (quotes as HomeState.Success).quotes,
                    onAddQuoteToFavorite = { viewModel.addToFavorite(it) }
                )
            }
        }
    }
}
