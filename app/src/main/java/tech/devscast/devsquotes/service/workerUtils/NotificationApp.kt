package tech.devscast.devsquotes.service.workerUtils

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import tech.devscast.devsquotes.util.NotificationConstant

class NotificationApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = NotificationConstant.name
            val descriptionText = NotificationConstant.description

            val importance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(NotificationConstant.DAILY_QUOTES_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}
