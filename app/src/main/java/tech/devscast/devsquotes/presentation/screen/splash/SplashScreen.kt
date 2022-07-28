package tech.devscast.devsquotes.presentation.screen.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import tech.devscast.devsquotes.R
import tech.devscast.devsquotes.app.navigation.Screen
import tech.devscast.devsquotes.presentation.theme.Item4
import tech.devscast.devsquotes.presentation.theme.White

@Composable
fun SplashScreen(navController: NavHostController) {

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        delay(2000)
        navController.navigate(Screen.Home.route)
    }

    Scaffold(
        scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState), backgroundColor = Item4
    ) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Center
        ) {
            Text(
                text = "DevCotes",
                style = MaterialTheme.typography.h1,
                color = White,
                overflow = TextOverflow.Ellipsis,
                fontSize = 40.sp,
                fontWeight = FontWeight.Medium,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily(Font(R.font.pacifico_regular))
            )
        }
    }
}