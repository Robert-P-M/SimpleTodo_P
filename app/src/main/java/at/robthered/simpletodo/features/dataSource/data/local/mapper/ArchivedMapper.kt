package at.robthered.simpletodo.features.dataSource.data.local.mapper

import at.robthered.simpletodo.features.core.utils.ext.toLong
import at.robthered.simpletodo.features.dataSource.data.local.entity.ArchivedEntity
import at.robthered.simpletodo.features.dataSource.domain.local.model.ArchivedModel
import kotlinx.datetime.Instant

fun ArchivedEntity.toArchivedModel(): ArchivedModel {
    return ArchivedModel(
        archivedId = archivedId,
        isArchived = isArchived,
        createdAt = Instant.fromEpochMilliseconds(createdAt),
        taskId = taskId
    )
}

fun ArchivedModel.toArchivedEntity(): ArchivedEntity {
    return ArchivedEntity(
        archivedId = archivedId,
        isArchived = isArchived,
        createdAt = createdAt.toLong(),
        taskId = taskId,
    )
}