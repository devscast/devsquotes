package tech.devscast.devsquotes.presentation.screen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.magnifier
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import tech.devscast.devsquotes.R
import tech.devscast.devsquotes.app.navigation.Screen
import tech.devscast.devsquotes.presentation.theme.FavoriteBotBlack

@Composable
fun TopPageBar(modifier: Modifier = Modifier,navController: NavController) {
    TopAppBar(
        modifier = modifier.height(70.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 0.dp
    ){
        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 5.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TopTitle()
            SettingButton(navController)
        }
    }
}

@Composable
fun TopTitle() {
    Column {
        Text(
            text = stringResource(id = R.string.app_name),
            style = TextStyle(
                color = FavoriteBotBlack,
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp
            )
        )
        Text(
            text = stringResource(id = R.string.home_sub_title),
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Italic,
                fontSize = 14.sp
            )
        )
    }
}
@Composable
fun SettingButton(navController: NavController) {
    IconButton(onClick = { navController.navigate(Screen.Setting.route) }) {
        Image(
            imageVector = Icons.Default.Settings,
            contentDescription = "settings",
            modifier = Modifier
                .size(30.dp)
        )
    }
}
