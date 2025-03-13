package at.robthered.simpletodo.features.dataSource.domain.local.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class SectionModel(
    val sectionId: Long? = null,
    val title: String = "",
    val createdAt: Instant = Clock.System.now(),
    val updatedAt: Instant = Clock.System.now(),
)