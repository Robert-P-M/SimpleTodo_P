package at.robthered.simpletodo.features.dataSource.domain.local.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class TaskModel(
    val taskId: Long? = null,
    val title: String = "",
    val description: String? = null,
    val createdAt: Instant = Clock.System.now(),
    val updatedAt: Instant = Clock.System.now(),
    val sectionId: Long? = null,
    val parentTaskId: Long? = null,
    val currentEventId: Long? = null,
)