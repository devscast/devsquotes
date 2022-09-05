package tech.devscast.devsquotes.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tech.devscast.devsquotes.data.model.Quote
import tech.devscast.devsquotes.util.removeDoubleQuotes

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavoriteItem(quote: Quote, onClick: (Quote) -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        onClick = {
            onClick(quote)
        }
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(16.dp)
        ) {
            Text(
                text = quote.fr.removeDoubleQuotes(),
                fontSize = 18.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = quote.author.removeDoubleQuotes(), fontSize = 16.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = quote.role.removeDoubleQuotes(), fontSize = 14.sp)
        }
    }
}
