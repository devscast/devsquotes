package tech.devscast.devsquotes.presentation.screen.favorites

import android.annotation.SuppressLint
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.zIndex
import tech.devscast.devsquotes.data.model.Quote
import tech.devscast.devsquotes.presentation.screen.FavoriteItem
import kotlin.math.roundToInt

private const val ANIMATION_DURATION = 500
private const val MIN_DRAG_AMOUNT = 6

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun DraggableCard(
    quote: Quote,
    onClick: (Quote) -> Unit,
    isRevealed: Boolean,
    onExpand: (Quote) -> Unit,
    onCollapse: () -> Unit,
) {
    val transitionState = remember {
        MutableTransitionState(isRevealed).apply {
            targetState = !isRevealed
        }
    }
    val transition = updateTransition(transitionState, "cardTransition")
    val cardBgColor by transition.animateColor(
        label = "cardBgColorTransition",
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION) },
        targetValueByState = {
            if (isRevealed) Color.Red else Color.White
        }
    )
    val offsetTransition by transition.animateFloat(
        label = "cardOffsetTransition",
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION) },
        targetValueByState = { if (isRevealed) 100f else 0f },
    )

    FavoriteItem(
        quote = quote, onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .zIndex(1f)
            .wrapContentHeight()
            .background(cardBgColor)
            .offset { IntOffset(-offsetTransition.roundToInt(), 0) }
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    when {
                        dragAmount < -MIN_DRAG_AMOUNT -> onExpand(quote)
                        dragAmount >= MIN_DRAG_AMOUNT -> onCollapse()
                    }
                }
            }
    )
}
