package at.robthered.simpletodo.features.alarm.data

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import at.robthered.simpletodo.MainActivity
import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.alarm.domain.NotificationData
import at.robthered.simpletodo.features.alarm.domain.AppNotificationManager

class AppNotificationManagerImpl(
    private val context: Context
) : AppNotificationManager {
    override fun showNotification(notificationData: NotificationData) {
        val notificationManager = context.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        val intent = Intent(context, MainActivity::class.java)
            .apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra(TASK_ID_KEY, notificationData.taskId)
            }
        val pendingIntent = PendingIntent
            .getActivity(context,
                notificationData.taskId.toInt(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)


        val notification = NotificationCompat.Builder(context, "task_reminder_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(notificationData.title)
            .setContentText(notificationData.message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .addAction(
                R.drawable.opentaskdetails,
                context.getString(R.string.notification_open_task_details),
                pendingIntent
            )
            .setContentIntent(pendingIntent)
            .build()

        Log.d("DEBUG", "notification: ${notification.toString()}")

        notificationManager.notify(notificationData.eventId.toInt(), notification)
    }

    companion object {
        const val TASK_ID_KEY = "task_id"
    }
}