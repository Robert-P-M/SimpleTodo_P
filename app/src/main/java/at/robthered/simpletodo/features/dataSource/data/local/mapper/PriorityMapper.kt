package at.robthered.simpletodo.features.dataSource.data.local.mapper

import at.robthered.simpletodo.features.core.utils.ext.toLong
import at.robthered.simpletodo.features.dataSource.data.local.entity.PriorityEntity
import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityModel
import kotlinx.datetime.Instant

fun PriorityEntity.toPriorityModel(): PriorityModel {
    return PriorityModel(
        priorityId = priorityId,
        priority = priority,
        createdAt = Instant.fromEpochMilliseconds(createdAt),
        taskId = taskId
    )
}

fun PriorityModel.toPriorityEntity(): PriorityEntity {
    return PriorityEntity(
        priorityId = priorityId,
        priority = priority,
        createdAt = createdAt.toLong(),
        taskId = taskId
    )
}