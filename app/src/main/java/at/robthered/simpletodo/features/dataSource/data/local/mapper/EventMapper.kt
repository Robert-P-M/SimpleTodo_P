package at.robthered.simpletodo.features.dataSource.data.local.mapper

import at.robthered.simpletodo.features.core.utils.ext.toLong
import at.robthered.simpletodo.features.dataSource.data.local.entity.EventEntity
import at.robthered.simpletodo.features.dataSource.domain.local.model.EventModel
import kotlinx.datetime.Instant

fun EventEntity.toEventModel(): EventModel {
    return EventModel(
        eventId = eventId,
        start = Instant.fromEpochMilliseconds(start),
        isActive = isActive,
        durationMinutes = durationMinutes,
        isCompleted = isCompleted,
        isWithTime = isWithTime,
        createdAt = Instant.fromEpochMilliseconds(createdAt),
        updatedAt = Instant.fromEpochMilliseconds(updatedAt),
        isNotificationEnabled = isNotificationEnabled,
        taskId = taskId,
    )
}

fun EventModel.toEventEntity(): EventEntity {
    return EventEntity(
        eventId = eventId,
        start = start.toLong(),
        isActive = isActive,
        durationMinutes = durationMinutes,
        isCompleted = isCompleted,
        isWithTime = isWithTime,
        createdAt = createdAt.toLong(),
        updatedAt = updatedAt.toLong(),
        isNotificationEnabled = isNotificationEnabled,
        taskId = taskId
    )
}