package tech.devscast.devsquotes.presentation.screen.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tech.devscast.devsquotes.data.model.Quote
import tech.devscast.devsquotes.presentation.theme.White
import tech.devscast.devsquotes.util.Constants.cornerRadiusBig
import tech.devscast.devsquotes.util.Constants.normalElevation

@Composable
fun CardItem(
    modifier: Modifier = Modifier,
    quote: Quote,
    color: Color,
    onAddToFavorite: (Quote) -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(cornerRadiusBig),
        elevation = normalElevation
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Center)
            ) {
                Text(
                    text = quote.fr,
                    style = MaterialTheme.typography.body1,
                    color = White,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    fontStyle = FontStyle.Italic
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = quote.author,
                    color = White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.align(Alignment.End)
                )
            }

            Icon(
                imageVector = Icons.Outlined.Favorite,
                tint = if (quote.is_favorite) Color.Red else Color.White,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .clickable(onClick = { onAddToFavorite(quote) })
            )
        }

    }


}
