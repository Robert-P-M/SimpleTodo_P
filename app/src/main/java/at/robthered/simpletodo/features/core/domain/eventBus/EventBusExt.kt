package at.robthered.simpletodo.features.core.domain.eventBus

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

suspend fun <T> EventBus<T>.clear(defaultEvent: T) {
    publish(defaultEvent)
}

inline fun <T, reified E : T> EventBus<T>.on(
    scope: CoroutineScope,
    crossinline action: suspend (E) -> Unit
): StateFlow<E?> {
    return this.events
        .filterIsInstance<E>()
        .onEach { action(it) }
        .stateIn(
            scope = scope,
            started = SharingStarted.Eagerly,
            initialValue = null
        )
}