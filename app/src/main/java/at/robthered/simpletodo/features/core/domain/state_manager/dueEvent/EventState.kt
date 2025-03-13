package at.robthered.simpletodo.features.core.domain.state_manager.dueEvent

import at.robthered.simpletodo.features.dataSource.domain.local.model.EventModel

data class EventState(
    val isEventActive: Boolean = false,
    val eventModel: EventModel = EventModel(),
)