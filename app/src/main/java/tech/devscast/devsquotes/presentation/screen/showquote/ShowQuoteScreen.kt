package tech.devscast.devsquotes.presentation.screen.showquote

import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import tech.devscast.devsquotes.app.navigation.Screen
import tech.devscast.devsquotes.data.model.Quote
import tech.devscast.devsquotes.presentation.screen.showquote.business.ShowQuoteState
import tech.devscast.devsquotes.presentation.screen.showquote.business.ShowQuoteViewModel
import tech.devscast.devsquotes.presentation.sharedcomponents.TopPageBar
import tech.devscast.devsquotes.util.getShareableText
import tech.devscast.devsquotes.util.removeDoubleQuotes


@Composable
fun ShowQuoteScreen(
    navController: NavController,
    quoteId: String = "",
    viewModel: ShowQuoteViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val quoteState by viewModel.quote.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.getQuoteById(quoteId)
    }

    Scaffold(
        topBar = {
            TopPageBar(
                title = "Devsquotes",
                subtitle = "",
                actionMenu = {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favoris",
                        modifier = Modifier
                            .clickable(
                                enabled = true,
                                onClick = { navController.navigate(Screen.Favorite.route) },
                                interactionSource = MutableInteractionSource(),
                                indication = null
                            )
                            .size(30.dp)
                            .padding(end = 8.dp)
                    )
                },
            )
        },
        content = {
            Crossfade(targetState = quoteState) { state ->
                Toast.makeText(context,state.toString(),Toast.LENGTH_SHORT).show()
                when (state) {
                    is ShowQuoteState.Loading -> {
                        CircularProgressIndicator()
                    }

                    is ShowQuoteState.Success -> {
                        ShowQuoteContent(
                            quote = state.quote,
                            onAddToFavorite = {
                                viewModel.addToFavorite(it)
                            })
                    }

                    is ShowQuoteState.Error -> {
                        // TODO
                    }
                    ShowQuoteState.Empty -> {
                        CircularProgressIndicator()
                    }
                    ShowQuoteState.Initial -> {}
                }
            }
        }
    )
}

@Composable
private fun ShowQuoteContent(quote: Quote, onAddToFavorite: (Quote) -> Unit) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, 32.dp)
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .align(Alignment.Center)
        ) {
            Text(text = quote.fr.removeDoubleQuotes(), fontSize = 20.sp)
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = quote.author.removeDoubleQuotes(),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(text = quote.role.removeDoubleQuotes(), fontSize = 14.sp)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            IconButton(
                onClick = { onAddToFavorite(quote) },
                modifier = Modifier
                    .size(20.dp)
                    .border(
                        width = 1.dp,
                        color = if (quote.is_favorite) Color.Red else Color.Green,
                        shape = CircleShape
                    ),
            ) {
                Icon(
                    imageVector = Icons.Outlined.Favorite,
                    tint = if (quote.is_favorite) Color.Red else Color.Green,
                    contentDescription = null,
                    modifier = Modifier.padding(0.dp)
                )
            }

            IconButton(
                onClick = {

                    val sendIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT,
                            quote.getShareableText()
                        )
                        putExtra(Intent.EXTRA_TITLE, "Partager la citation")
                        type = "text/plain"
                    }


                    context.startActivity(sendIntent)
                },
                modifier = Modifier
                    .size(20.dp)
                    .border(width = 1.dp, color = Color.Green, shape = CircleShape),
            ) {
                Icon(
                    imageVector = Icons.Outlined.Share,
                    tint = Color.Green,
                    contentDescription = null,
                    modifier = Modifier.padding(0.dp)
                )
            }
        }
    }
}