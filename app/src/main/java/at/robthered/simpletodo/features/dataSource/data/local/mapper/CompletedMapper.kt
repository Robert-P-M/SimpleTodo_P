package at.robthered.simpletodo.features.dataSource.data.local.mapper

import at.robthered.simpletodo.features.core.utils.ext.toLong
import at.robthered.simpletodo.features.dataSource.data.local.entity.CompletedEntity
import at.robthered.simpletodo.features.dataSource.domain.local.model.CompletedModel
import kotlinx.datetime.Instant

fun CompletedEntity.toCompletedModel(): CompletedModel {
    return CompletedModel(
        completedId = completedId,
        isCompleted = isCompleted,
        createdAt = Instant.fromEpochMilliseconds(createdAt),
        taskId = taskId
    )
}

fun CompletedModel.toCompletedEntity(): CompletedEntity {
    return CompletedEntity(
        isCompleted = isCompleted,
        createdAt = createdAt.toLong(),
        taskId = taskId
    )
}