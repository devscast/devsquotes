package tech.devscast.devsquotes.presentation.screen.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import tech.devscast.devsquotes.data.model.Quote
import tech.devscast.devsquotes.util.Constants.color1
import tech.devscast.devsquotes.util.Constants.color2
import tech.devscast.devsquotes.util.Constants.color3
import tech.devscast.devsquotes.util.swipeCardModifier
import timber.log.Timber

@Composable
fun SwipeableCard(
    posts: List<Quote>, //selectedItem: (Quote) -> (Unit),
) {

    LaunchedEffect(Unit) {
        Timber.e(posts.toString())
    }
    val visibleCard: Int = StrictMath.min(3, posts.size)
    val firstCard = remember { mutableStateOf(0) }


    fun rearrangeForward() {
        if (firstCard.value != (posts.size - 2)) {
            firstCard.value++
        } else {
            firstCard.value = 0
        }
    }

    fun rearrangeBackward() {
        if (firstCard.value != 0) {
            firstCard.value--
        } else {
            firstCard.value = posts.size - 2
        }
    }

    Box(Modifier.fillMaxWidth()) {
        repeat(visibleCard) { index ->
            CardItem(
                modifier = Modifier.swipeCardModifier(
                    cardIndex = index,
                    rearrangeForward = ::rearrangeForward,
                    rearrangeBackward = ::rearrangeBackward
                ),
                post = posts[if (index == 0) firstCard.value else firstCard.value + 1],
                color = Color.Green
            )
        }
    }
}