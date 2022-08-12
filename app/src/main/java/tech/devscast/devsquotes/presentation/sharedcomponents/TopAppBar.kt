package tech.devscast.devsquotes.presentation.screen.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
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
import tech.devscast.devsquotes.R
import tech.devscast.devsquotes.presentation.theme.FavoriteBotBlack

@Composable
fun TopPageBar(title: String, subtitle: String, modifier: Modifier = Modifier, actionMenu: @Composable () -> Unit = {}) {
    TopAppBar(
        modifier = modifier.height(70.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 5.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TopTitle(title,subtitle)
            actionMenu()
        }
    }
}

@Composable
private fun TopTitle(title: String, subtitle: String) {
    Column {
        Text(
            text = title,
            style = TextStyle(
                color = FavoriteBotBlack,
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp
            )
        )
        Text(
            text = subtitle,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Italic,
                fontSize = 14.sp
            )
        )
    }
}
