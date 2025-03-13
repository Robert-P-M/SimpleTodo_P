package at.robthered.simpletodo.features.alarm.domain

interface ShowNotificationUseCase {
    suspend operator fun invoke(eventId: Long)
}