package tech.devscast.devsquotes.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.AndroidEntryPoint
import tech.devscast.devsquotes.app.navigation.MainNavGraph
import tech.devscast.devsquotes.presentation.theme.DevsquotesTheme
import tech.devscast.devsquotes.service.workmanager.NotificationWorkManager
import java.util.Calendar
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpWorkManager()

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

    private fun setUpWorkManager() {
        val currentDate = Calendar.getInstance()
        val notificationTime = Calendar.getInstance()

        notificationTime.set(Calendar.HOUR_OF_DAY, 8)
        notificationTime.set(Calendar.MINUTE, 0)
        notificationTime.set(Calendar.SECOND, 0)

        if (notificationTime.before(currentDate)) {
            notificationTime.add(Calendar.HOUR_OF_DAY, 24)
        }
        val timeDiff = notificationTime.timeInMillis - currentDate.timeInMillis

        val dailyWorkRequest = OneTimeWorkRequestBuilder<NotificationWorkManager>()
            .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
            .addTag("TAG_OUTPUT")
            .build()

        WorkManager.getInstance(applicationContext)
            .enqueue(dailyWorkRequest)
    }
}
