package at.robthered.simpletodo.features.core.data.eventBus

import at.robthered.simpletodo.features.core.domain.eventBus.EventBus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class EventBusImpl<T>: EventBus<T> {
    private val _events = MutableStateFlow<T?>(null)
    override val events: Flow<T?>
        get() = _events.asStateFlow()

    override suspend fun publish(event: T?) {
        _events.emit(event)
    }
}