package at.robthered.simpletodo.features.core.domain.state_manager.event

import at.robthered.simpletodo.features.dataSource.domain.local.model.EventModel

sealed interface EventStateManagerAction {
    data class OnUpdateEventModel(val eventModel: EventModel?): EventStateManagerAction
    data object OnResetEvent: EventStateManagerAction
    data class OnInitializeState(val eventModel: EventModel?): EventStateManagerAction
    data object OnSendCurrentEvent: EventStateManagerAction
}