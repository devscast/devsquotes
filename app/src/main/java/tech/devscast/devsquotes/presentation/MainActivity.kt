package tech.devscast.devsquotes.presentation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.AndroidEntryPoint
import tech.devscast.devsquotes.app.navigation.MainNavGraph
import tech.devscast.devsquotes.presentation.theme.DevsquotesTheme
import tech.devscast.devsquotes.service.workmanager.NotificationWorkManager
import tech.devscast.devsquotes.util.NotificationConstant
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val editor = sharedPreferences.edit()
        editor.apply {
            putBoolean("is_session_start", true)
        }.apply()

        createNotificationChannel()
        if (!sharedPreferences.getBoolean("is_session_start", false)){
            startWork()
        }

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

    private fun startWork(){
        WorkManager.getInstance(applicationContext)
            .enqueueUniquePeriodicWork(
                NotificationConstant.name,
                ExistingPeriodicWorkPolicy.KEEP,
                setUpWorkManager()
            )
    }

    private fun setUpWorkManager() = PeriodicWorkRequestBuilder<NotificationWorkManager>(1, TimeUnit.DAYS)
        .setInitialDelay(dailyWorker(), TimeUnit.MICROSECONDS)
        .addTag(NotificationConstant.TAG_OUTPUT)
        .build()

    private fun dailyWorker(): Long{
        val dueDate = Calendar.getInstance()

        dueDate.set(Calendar.HOUR_OF_DAY, 6)
        dueDate.set(Calendar.MINUTE, 20)
        dueDate.set(Calendar.SECOND, 0)

        return dueDate.timeInMillis - System.currentTimeMillis()
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
