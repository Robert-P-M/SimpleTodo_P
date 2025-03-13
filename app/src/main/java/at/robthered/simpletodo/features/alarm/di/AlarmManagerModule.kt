package at.robthered.simpletodo.features.alarm.di

import android.content.Context
import at.robthered.simpletodo.features.alarm.data.AlarmSchedulerImpl
import at.robthered.simpletodo.features.alarm.data.AppNotificationManagerImpl
import at.robthered.simpletodo.features.alarm.data.ShowNotificationUseCaseImpl
import at.robthered.simpletodo.features.alarm.domain.AlarmScheduler
import at.robthered.simpletodo.features.alarm.domain.AppNotificationManager
import at.robthered.simpletodo.features.alarm.domain.ScheduleAlarmUseCase
import at.robthered.simpletodo.features.alarm.data.ScheduleAlarmUseCaseImpl
import at.robthered.simpletodo.features.alarm.domain.ShowNotificationUseCase
import at.robthered.simpletodo.features.dataSource.domain.repository.task.TaskRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val alarmManagerModule = module {

    single<AlarmScheduler> {
        AlarmSchedulerImpl(
            context = androidContext()
        )
    }
    single<AppNotificationManager> {
        AppNotificationManagerImpl(
            context = androidContext()
        )
    }
    single<ScheduleAlarmUseCase> {
        ScheduleAlarmUseCaseImpl(
            alarmScheduler = get<AlarmScheduler>()
        )
    }
    single<ShowNotificationUseCase> {
        ShowNotificationUseCaseImpl(
            notificationManager = get<AppNotificationManager>(),
            taskRepository = get<TaskRepository>()
        )
    }
}