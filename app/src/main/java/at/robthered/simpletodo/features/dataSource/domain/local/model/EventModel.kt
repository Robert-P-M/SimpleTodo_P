package at.robthered.simpletodo.features.dataSource.domain.local.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration.Companion.minutes

data class EventModel(
    val eventId: Long? = null,
    val start: Instant = Clock.System.now(),
    val isActive: Boolean = false,
    val durationMinutes: Int? = null,
    val isCompleted: Boolean = false,
    val isWithTime: Boolean = false,
    val createdAt: Instant = Clock.System.now(),
    val updatedAt: Instant = Clock.System.now(),
    val isNotificationEnabled: Boolean = false,
    val taskId: Long? = null,
) {
    val end: Instant?
        get() = if (isFullDay) {
            durationMinutes?.let { start.plus(it.minutes) }
        } else null
    val isFullDay: Boolean
        get() = durationMinutes != null
}

val emptyEventModel = EventModel()