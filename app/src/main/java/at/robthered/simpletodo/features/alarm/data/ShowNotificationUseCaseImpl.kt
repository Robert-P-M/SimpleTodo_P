package at.robthered.simpletodo.features.alarm.data

import at.robthered.simpletodo.features.alarm.domain.NotificationData
import at.robthered.simpletodo.features.alarm.domain.AppNotificationManager
import at.robthered.simpletodo.features.alarm.domain.ShowNotificationUseCase
import at.robthered.simpletodo.features.dataSource.domain.repository.task.TaskRepository
import kotlinx.coroutines.flow.firstOrNull

class ShowNotificationUseCaseImpl(
    private val notificationManager: AppNotificationManager,
    private val taskRepository: TaskRepository,
) : ShowNotificationUseCase {
    override suspend operator fun invoke(eventId: Long) {
        val task = taskRepository.getTaskOfEvent(eventId = eventId)
            .firstOrNull()
        if(task != null) {
            val notificationData: NotificationData = NotificationData(
                eventId = task.currentEventId!!,
                taskId = task.taskId!!,
                title = "You have an upcoming task!",
                message = task.title
            )
            notificationManager.showNotification(notificationData)
        }
    }
}