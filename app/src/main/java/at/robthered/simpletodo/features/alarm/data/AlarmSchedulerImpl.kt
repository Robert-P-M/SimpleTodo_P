package at.robthered.simpletodo.features.alarm.data

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import at.robthered.simpletodo.features.alarm.domain.AlarmScheduler
import at.robthered.simpletodo.features.alarm.presentation.EventAlarmReceiver

class AlarmSchedulerImpl(
    private val context: Context
) : AlarmScheduler {
    override fun scheduleAlarm(eventId: Long, triggerAtMillis: Long) {
        val intent = Intent(context, EventAlarmReceiver::class.java).apply {
            putExtra(EVENT_ID, eventId)
        }

        val pendingIntent = PendingIntent
            .getBroadcast(
                context, eventId.toInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        Log.d("DEBUG", "AlarmSchedulerImpl - eventId: $eventId - triggerAtMillis: $triggerAtMillis")
        alarmManager.setAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            pendingIntent,
        )
    }

    override fun cancelAlarm(eventId: Long) {
        val intent = Intent(context, EventAlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context, eventId.toInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }

    companion object {
        const val EVENT_ID = "EVENT_ID"
    }
}