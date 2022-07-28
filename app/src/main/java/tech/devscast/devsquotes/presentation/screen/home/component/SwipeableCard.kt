package tech.devscast.devsquotes.presentation.screen.home.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import tech.devscast.devsquotes.data.model.Quote
import tech.devscast.devsquotes.presentation.theme.Item4
import tech.devscast.devsquotes.util.swipeCardModifier

@Composable
fun SwipeableCard(
    posts: List<Quote>, // selectedItem: (Quote) -> (Unit),
) {
    val visibleCard: Int = StrictMath.min(3, posts.size)
    val firstCard = remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    val offset: Animatable<Offset, AnimationVector2D> = remember {
        Animatable(
            Offset(0f, 0f),
            Offset.VectorConverter
        )
    }

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
                    scope = scope,
                    offset = offset,
                    rearrangeForward = ::rearrangeForward,
                    rearrangeBackward = ::rearrangeBackward
                ),
                post = posts[if (index == 0) firstCard.value else firstCard.value + 1],
                color = Item4
            )
        }
    }
}
