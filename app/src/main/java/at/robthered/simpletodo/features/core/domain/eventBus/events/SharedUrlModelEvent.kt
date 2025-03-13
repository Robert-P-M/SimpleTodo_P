package at.robthered.simpletodo.features.core.domain.eventBus.events

import at.robthered.simpletodo.features.network.domain.model.SharedUrlModel

sealed class SharedUrlModelEvent {
    data class NewSharedUrlModelEvent(val sharedUrlModel: SharedUrlModel): SharedUrlModelEvent()
    data object ClearEvent: SharedUrlModelEvent()
}