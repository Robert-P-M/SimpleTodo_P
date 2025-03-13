package at.robthered.simpletodo.features.dataSource.data.local.mapper

import at.robthered.simpletodo.features.core.utils.ext.toLong
import at.robthered.simpletodo.features.dataSource.data.local.entity.LinkEntity
import at.robthered.simpletodo.features.dataSource.domain.local.model.LinkModel
import kotlinx.datetime.Instant

fun LinkEntity.toLinkModel(): LinkModel {
    return LinkModel(
        linkId = linkId,
        title = title,
        description = description,
        linkUrl = linkUrl,
        imageFilePath = imageFilePath,
        createdAt = Instant.fromEpochMilliseconds(createdAt),
        updatedAt = Instant.fromEpochMilliseconds(updatedAt),
        taskId = taskId
    )
}

fun LinkModel.toLinkEntity(): LinkEntity {
    return LinkEntity(
        linkId = linkId,
        title = title,
        description = description,
        linkUrl = linkUrl,
        imageFilePath = imageFilePath,
        createdAt = createdAt.toLong(),
        updatedAt = updatedAt.toLong(),
        taskId = taskId
    )
}