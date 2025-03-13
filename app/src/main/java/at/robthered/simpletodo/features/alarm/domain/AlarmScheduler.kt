package at.robthered.simpletodo.features.alarm.domain

interface AlarmScheduler {
    fun scheduleAlarm(eventId: Long, triggerAtMillis: Long)
    fun cancelAlarm(eventId: Long)
}