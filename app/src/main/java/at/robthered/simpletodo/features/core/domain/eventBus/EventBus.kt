package at.robthered.simpletodo.features.core.domain.eventBus

import kotlinx.coroutines.flow.Flow

interface EventBus<T> {
    val events: Flow<T?>
    suspend fun publish(event: T?)
}