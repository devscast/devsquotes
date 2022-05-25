package tech.devscast.devsquotes.presentation.screen.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tech.devscast.devsquotes.data.model.Quote
import tech.devscast.devsquotes.util.Constants.cornerRadiusBig
import tech.devscast.devsquotes.util.Constants.normalElevation

@Composable
fun CardItem(
    modifier: Modifier = Modifier,
    post: Quote, //selectedItem: (Quote) -> (Unit)
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(cornerRadiusBig),
        elevation = normalElevation
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                /*.clickable { selectedItem(post) }*/,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = post.fr,
                    style = MaterialTheme.typography.body1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    fontStyle = FontStyle.Italic
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = post.author,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}