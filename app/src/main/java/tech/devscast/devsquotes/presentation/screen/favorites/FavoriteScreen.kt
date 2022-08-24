package tech.devscast.devsquotes.presentation.screen.favorites

import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import tech.devscast.devsquotes.data.model.Quote
import tech.devscast.devsquotes.presentation.screen.favorites.business.FavoriteState
import tech.devscast.devsquotes.presentation.screen.favorites.business.FavoriteViewModel
import tech.devscast.devsquotes.presentation.screen.home.component.SwipeableCard
import tech.devscast.devsquotes.presentation.screen.home.component.TopPageBar

@Composable
fun FavoriteScreen(navController: NavController, viewModel: FavoriteViewModel = hiltViewModel()) {

    val quotesState by viewModel.quotesState.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopPageBar(title = "Favoris", "Vos citations favorites")
        },
    ) {
        Crossfade(targetState = quotesState) { favoriteState ->
            when (favoriteState) {
                is FavoriteState.Success -> {
                    FavoriteScreenContent(
                        quotes = favoriteState.quotes,
                        removeFavorite = { viewModel.removeFromFavorites(it) }
                    )
                }
                is FavoriteState.Loading -> {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
                is FavoriteState.Empty -> {
                    Toast.makeText(context, "Vide", Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }
}

@Composable
private fun FavoriteScreenContent(quotes: List<Quote>, removeFavorite: (Quote) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        SwipeableCard(
            quotes,
            onAddQuoteToFavorite = removeFavorite
        )
    }
}
