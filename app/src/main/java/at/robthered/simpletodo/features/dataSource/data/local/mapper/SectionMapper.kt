package at.robthered.simpletodo.features.dataSource.data.local.mapper

import at.robthered.simpletodo.features.core.utils.ext.toLong
import at.robthered.simpletodo.features.dataSource.data.local.entity.SectionEntity
import at.robthered.simpletodo.features.dataSource.domain.local.model.SectionModel
import kotlinx.datetime.Instant

fun SectionEntity.toSectionModel(): SectionModel {
    return SectionModel(
        sectionId = sectionId,
        title = title,
        createdAt = Instant.fromEpochMilliseconds(createdAt),
        updatedAt = Instant.fromEpochMilliseconds(updatedAt),
    )
}

fun SectionModel.toSectionEntity(): SectionEntity {
    return SectionEntity(
        sectionId = sectionId,
        title = title,
        createdAt = createdAt.toLong(),
        updatedAt = updatedAt.toLong(),
    )
}