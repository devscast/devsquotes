package tech.devscast.devsquotes.service.workmanager

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import tech.devscast.devsquotes.R
import tech.devscast.devsquotes.data.model.Quote
import tech.devscast.devsquotes.data.model.generatedId
import tech.devscast.devsquotes.data.repository.QuotesRepository
import tech.devscast.devsquotes.presentation.MainActivity
import tech.devscast.devsquotes.util.NotificationConstant
import tech.devscast.devsquotes.util.removeDoubleQuotes

@HiltWorker
class NotificationWorkManager @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: QuotesRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val quote = repository.getNonShownQuote()
        showNotification(quote)
        repository.setAsShown(quote)
        return Result.success()
    }

    private fun showNotification(quote: Quote) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            "https://quotes.devscast.tech?id=${quote.generatedId}".toUri(),
            applicationContext,
            MainActivity::class.java
        ).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = TaskStackBuilder.create(applicationContext).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
        }

        val builder = NotificationCompat.Builder(
            applicationContext,
            NotificationConstant.DAILY_QUOTES_CHANNEL_ID
        )
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(quote.author.removeDoubleQuotes())
            .setContentText(quote.fr.removeDoubleQuotes())
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(quote.fr.removeDoubleQuotes())
            )
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(applicationContext)) {
            notify(NotificationConstant.ID, builder.build())
        }
    }
}
