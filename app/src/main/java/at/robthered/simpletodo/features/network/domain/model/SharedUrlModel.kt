package at.robthered.simpletodo.features.network.domain.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class SharedUrlModel(
    val taskId: Long? = null,
    val title: String,
    val description: String? = null,
    val link: String,
    val imageUrl: String?,
    val createdAt: Instant = Clock.System.now(),
    val updatedAt: Instant = Clock.System.now(),
)