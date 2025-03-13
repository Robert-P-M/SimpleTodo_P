package at.robthered.simpletodo.features.dataSource.data.local.mapper

import at.robthered.simpletodo.features.core.utils.ext.toInstant
import at.robthered.simpletodo.features.core.utils.ext.toLong
import at.robthered.simpletodo.features.dataSource.data.local.entity.TaskEntity
import at.robthered.simpletodo.features.dataSource.domain.local.model.TaskModel

fun TaskEntity.toTaskModel(): TaskModel {
    return TaskModel(
        taskId = taskId,
        title = title,
        description = description,
        createdAt = createdAt.toInstant(),
        updatedAt = updatedAt.toInstant(),
        sectionId = sectionId,
        parentTaskId = parentTaskId,
        currentEventId = currentEventId
    )
}

fun TaskModel.toTaskEntity(): TaskEntity {
    return TaskEntity(
        taskId = taskId,
        title = title,
        description = description,
        createdAt = createdAt.toLong(),
        updatedAt = updatedAt.toLong(),
        sectionId = sectionId,
        parentTaskId = parentTaskId,
        currentEventId = currentEventId
    )
}