package at.robthered.simpletodo.features.alarm.domain

interface ScheduleAlarmUseCase {
    operator fun invoke(eventId: Long, triggerAtMillis: Long)
}