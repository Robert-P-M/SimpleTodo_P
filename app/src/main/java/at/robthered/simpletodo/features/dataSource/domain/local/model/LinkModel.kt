package at.robthered.simpletodo.features.dataSource.domain.local.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class LinkModel(
    val linkId: Long? = null,
    val title: String,
    val description: String? = null,
    val linkUrl: String,
    val imageFilePath: String? = null,
    val createdAt: Instant = Clock.System.now(),
    val updatedAt: Instant = Clock.System.now(),
    val taskId: Long? = null
)