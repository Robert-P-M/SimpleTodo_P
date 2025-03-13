package at.robthered.simpletodo.features.dataSource.domain.local.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class PriorityModel(
    val priorityId: Long? = null,
    val priority: PriorityEnum? = null,
    val createdAt: Instant = Clock.System.now(),
    val taskId: Long? = null,
)