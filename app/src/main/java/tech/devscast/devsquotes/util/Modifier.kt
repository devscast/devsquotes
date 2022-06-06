package tech.devscast.devsquotes.util

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.zIndex
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.roundToInt

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.swipeCardModifier(
    cardIndex: Int,
    rearrangeForward: () -> Unit,
    rearrangeBackward: () -> Unit
): Modifier {

    val zIndex = Constants.TOP_Z_INDEX - cardIndex


    val offset: Animatable<Offset, AnimationVector2D> = Animatable(
        Offset(0f, 0f), Offset.VectorConverter
    )

    val scale = calculateScale(cardIndex)
    val offsetY = calculateOffset(cardIndex)

    if (cardIndex > Constants.TOP_CARD_INDEX) {
        this
            .graphicsLayer {
                translationY =
                    if (offset.value.y != 0f) min(
                        abs(offset.value.y),
                        Constants.paddingOffset * 1.1f
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
            .width(Constants.cardWith)
            .height(Constants.cardHeight)
    } else {
        this
            .scale(scale)
            .offset { IntOffset(offset.value.x.roundToInt(), offset.value.y.roundToInt()) }
            .zIndex(zIndex)
            .width(Constants.cardWith)
            .height(Constants.cardHeight)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        CoroutineScope(Dispatchers.Main).launch {
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
                    CoroutineScope(Dispatchers.Main).launch {
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
                            animationSpec = tween(
                                durationMillis = 150,
                                easing = LinearEasing
                            )
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

    return this
}

private fun calculateScale(index: Int): Float {
    return when (index) {
        1 -> 0.97f
        2 -> 0.94f
        else -> 1f
    }
}

private fun calculateOffset(index: Int): Int {
    return if (index <= 2) {
        -(Constants.paddingOffset * index * 1.1).toInt()
    } else {
        -Constants.paddingOffset.toInt()
    }
}