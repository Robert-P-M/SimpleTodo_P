package at.robthered.simpletodo.features.alarm.data

import at.robthered.simpletodo.features.alarm.domain.AlarmScheduler
import at.robthered.simpletodo.features.alarm.domain.ScheduleAlarmUseCase

class ScheduleAlarmUseCaseImpl(
    private val alarmScheduler: AlarmScheduler
) : ScheduleAlarmUseCase {
    override fun invoke(eventId: Long, triggerAtMillis: Long) {
        alarmScheduler.scheduleAlarm(eventId, triggerAtMillis)
    }
}