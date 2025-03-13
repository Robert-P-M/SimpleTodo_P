package at.robthered.simpletodo.features.alarm.domain

data class NotificationData(
    val eventId: Long,
    val taskId: Long,
    val title: String,
    val message: String,
)

interface AppNotificationManager {
    fun showNotification(notificationData: NotificationData)
}