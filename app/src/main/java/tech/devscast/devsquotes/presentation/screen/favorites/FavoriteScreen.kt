package tech.devscast.devsquotes.presentation.screen.favorites

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import tech.devscast.devsquotes.app.navigation.Screen
import tech.devscast.devsquotes.data.model.Quote
import tech.devscast.devsquotes.data.model.generatedId
import tech.devscast.devsquotes.presentation.screen.favorites.business.FavoriteState
import tech.devscast.devsquotes.presentation.screen.favorites.business.FavoriteViewModel
import tech.devscast.devsquotes.presentation.screen.showquote.business.ShowQuoteState
import tech.devscast.devsquotes.presentation.sharedcomponents.EmptyComponent
import tech.devscast.devsquotes.presentation.sharedcomponents.LoadingComponent
import tech.devscast.devsquotes.presentation.sharedcomponents.TopPageBar

@Composable
fun FavoriteScreen(navController: NavController, viewModel: FavoriteViewModel = hiltViewModel()) {

    val quotesState by viewModel.quotesState.collectAsState()

    val context = LocalContext.current
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopPageBar(title = "Favoris", "Vos citations favorites")
        },
    ) {
        Crossfade(targetState = quotesState) { favoriteState ->
            when (favoriteState) {
                is FavoriteState.Success -> {
                    FavoriteScreenContent(
                        quotes = favoriteState.quotes,
                        onFavoriteClick = { navController.navigate("${Screen.Quote.route}?id=${it.generatedId}") },
                        removeFavorite = { viewModel.removeFromFavorites(it) }
                    )
                }
                is FavoriteState.Loading -> {
                    LoadingComponent(modifier = Modifier.fillMaxSize())
                }
                is FavoriteState.Empty -> {
                    EmptyComponent(text = "Aucune citation favorite")
                }
                is FavoriteState.Error -> {
                    EmptyComponent()
                }
            }
        }
    }
}

@Composable
private fun FavoriteScreenContent(
    quotes: List<Quote>,
    onFavoriteClick: (Quote) -> Unit,
    removeFavorite: (Quote) -> Unit
) {
    var revealedItem by remember {
        mutableStateOf<Quote?>(null)
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
    ) {

        items(quotes) { quote ->
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)) {


                DraggableCard(
                    quote = quote,
                    onClick = { onFavoriteClick(it) },
                    isRevealed = revealedItem == quote,
                    onExpand = { revealedItem = it },
                    onCollapse = { revealedItem = null })

                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .zIndex(if (revealedItem == quote) 1f else 0.1f)
                        .align(Alignment.CenterEnd)
                        .padding(end = 8.dp)
                        .clickable { removeFavorite(quote) }
                )

            }
        }
    }
}
