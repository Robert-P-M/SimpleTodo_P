package at.robthered.simpletodo.features.dataSource.domain.local.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class CompletedModel(
    val completedId: Long? = null,
    val isCompleted: Boolean = false,
    val createdAt: Instant = Clock.System.now(),
    val taskId: Long? = null,
)