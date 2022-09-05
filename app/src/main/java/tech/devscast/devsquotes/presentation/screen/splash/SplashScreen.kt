package tech.devscast.devsquotes.presentation.screen.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import tech.devscast.devsquotes.R

@Composable
fun SplashScreen(next : () -> Unit) {

    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 900,
                easing = {
                    OvershootInterpolator(5f).getInterpolation(it)
                })
        )
        delay(500L)
        next()
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
                .size(120.dp)
                .scale(scale.value),
            contentScale = ContentScale.Fit
        )

        Image(
            painter = painterResource(id = R.drawable.devscast),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .height(120.dp)
                .width(140.dp),
            contentScale = ContentScale.Inside
        )
    }
}