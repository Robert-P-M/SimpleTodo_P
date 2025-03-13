package at.robthered.simpletodo.features.core.domain.eventBus.events

import at.robthered.simpletodo.features.dataSource.domain.local.model.EventModel

sealed class DueEvent {
    data class NewEventModel(val eventModel: EventModel): DueEvent()
    data class CurrentEventModel(val eventModel: EventModel? = null): DueEvent()
    data object ClearEvent: DueEvent()
}