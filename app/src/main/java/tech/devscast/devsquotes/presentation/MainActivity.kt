package tech.devscast.devsquotes.presentation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.work.OutOfQuotaPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.AndroidEntryPoint
import tech.devscast.devsquotes.R
import tech.devscast.devsquotes.app.navigation.MainNavGraph
import tech.devscast.devsquotes.presentation.theme.DevsquotesTheme
import tech.devscast.devsquotes.service.workmanager.NotificationWorkManager
import tech.devscast.devsquotes.util.NotificationConstant
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createNotificationChannel()
        setUpWorkManager(this)

        setContent {
            DevsquotesTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainNavGraph(navController)
                }
            }
        }
    }

    private fun setUpWorkManager(context: Context) {
        val notificationWorkManager =
            PeriodicWorkRequestBuilder<NotificationWorkManager>(24, TimeUnit.HOURS)
                .build()

        WorkManager.getInstance(context)
            .enqueue(notificationWorkManager)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = NotificationConstant.name
            val descriptionText = NotificationConstant.description

            val importance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(NotificationConstant.DAILY_QUOTES_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
