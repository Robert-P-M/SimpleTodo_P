package at.robthered.simpletodo.features.dataSource.domain.local.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class ArchivedModel(
    val archivedId: Long? = null,
    val isArchived: Boolean = false,
    val createdAt: Instant = Clock.System.now(),
    val taskId: Long? = null,
)