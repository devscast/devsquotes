package tech.devscast.devsquotes.presentation.screen.component

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import tech.devscast.devsquotes.data.model.Quote
import tech.devscast.devsquotes.util.Constants.TOP_CARD_INDEX
import tech.devscast.devsquotes.util.Constants.TOP_Z_INDEX
import tech.devscast.devsquotes.util.Constants.cardHeight
import tech.devscast.devsquotes.util.Constants.cardWith
import tech.devscast.devsquotes.util.Constants.paddingOffset
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.roundToInt

@Composable
fun SwipeableCard(
    posts: List<Quote>, //selectedItem: (Quote) -> (Unit),
) {
    val visibleCard: Int = StrictMath.min(3, posts.size)
    val scope = rememberCoroutineScope()
    val firstCard = remember { mutableStateOf(0) }
    val offset: Animatable<Offset, AnimationVector2D> = remember {
        Animatable(
            Offset(0f, 0f),
            Offset.VectorConverter
        )
    }
    val animationSpec: FiniteAnimationSpec<Offset> = tween(
        durationMillis = 150,
        easing = LinearEasing
    )
    fun rearrangeForward() {
        if (firstCard.value == posts.size - 1) {
            firstCard.value = 0
        } else firstCard.value++
    }

    fun rearrangeBackward() {
        if (firstCard.value == -(posts.size - 1)) {
            firstCard.value = posts.size - 1
        } else firstCard.value--
    }

    Box(Modifier.fillMaxWidth()) {
        repeat(visibleCard) { index ->
            val zIndex = TOP_Z_INDEX - index
            val scale = calculateScale(index)
            val offsetY = calculateOffset(index)
            val cardModifier =
                makeCardModifier(
                    scope = scope,
                    cardIndex = index,
                    scale = scale,
                    zIndex = zIndex,
                    offsetY = offsetY,
                    offset = offset,
                    rearrangeForward = { rearrangeForward() },
                    rearrangeBackward = { rearrangeBackward() },
                    animationSpec = animationSpec
                )

            CardItem(
                modifier = cardModifier,
                post = posts[if (index == 0) firstCard.value else firstCard.value + 1],
                //selectedItem = selectedItem
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("ModifierFactoryExtensionFunction")
fun makeCardModifier(
    scope: CoroutineScope,
    cardIndex: Int,
    scale: Float,
    zIndex: Float,
    offset: Animatable<Offset, AnimationVector2D>,
    animationSpec: FiniteAnimationSpec<Offset>,
    offsetY: Int,
    rearrangeForward: () -> Unit,
    rearrangeBackward: () -> Unit
): Modifier {
    return if (cardIndex > TOP_CARD_INDEX) Modifier
        .graphicsLayer {
            translationY =
                if (offset.value.y != 0f) min(
                    abs(offset.value.y),
                    paddingOffset * 1.1f
                ) else 0f
            scaleX = if (offset.value.y != 0f) {
                min(scale + (abs(offset.value.y) / 1000), 1.06f - (cardIndex * 0.03f))
            } else scale
            scaleY = if (offset.value.y != 0f) {
                min(scale + (abs(offset.value.y) / 1000), 1.06f - (cardIndex * 0.03f))
            } else scale
        }
        .scale(scale)
        .offset { IntOffset(-offsetY, 0) }
        .zIndex(zIndex)
        .width(cardWith)
        .height(cardHeight)
    else Modifier
        .scale(scale)
        .offset { IntOffset(offset.value.x.roundToInt(), offset.value.y.roundToInt()) }
        .zIndex(zIndex)
        .width(cardWith)
        .height(cardHeight)
        .pointerInput(Unit) {
            detectTapGestures(
                onTap = {
                    scope.launch {
                        rearrangeBackward()
                        offset.animateTo(
                            targetValue = Offset(-600f, 600f),
                            animationSpec = snap()
                        )
                        offset.animateTo(
                            targetValue = Offset(0f, 0f),
                            animationSpec = tween(
                                durationMillis = 300,
                                easing = LinearEasing
                            )
                        )
                    }
                }
            )
        }
        .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                val dragOffset = Offset(
                    offset.value.x + change.positionChange().x,
                    offset.value.y + change.positionChange().y
                )
                scope.launch {
                    offset.snapTo(dragOffset)
                    change.consumePositionChange()
                    val x = when {

                        offset.value.x > 250 -> size.width.toFloat()
                        offset.value.x < -250 -> -size.width.toFloat()
                        else -> 0f
                    }
                    val y = when {

                        offset.value.y > 250 -> size.height.toFloat()
                        offset.value.y < -250 -> -size.height.toFloat()
                        else -> 0f
                    }

                    offset.animateTo(
                        targetValue = Offset(x, y),
                        animationSpec = animationSpec
                    )
                    if (abs(offset.value.x) == size.width.toFloat() || abs(offset.value.y) == size.height.toFloat()) {
                        rearrangeForward()
                        offset.animateTo(
                            targetValue = Offset(0f, 0f),
                            animationSpec = snap()
                        )
                    }
                }
            }
        }
}

private fun calculateScale(idx: Int): Float {
    return when (idx) {
        1 -> 0.97f
        2 -> 0.94f
        else -> 1f
    }
}

private fun calculateOffset(idx: Int): Int {
    return when (idx) {
        1 -> -(paddingOffset * idx * 1.1).toInt()
        2 -> -(paddingOffset * idx * 1.1).toInt()
        else -> -paddingOffset.toInt()
    }
}
