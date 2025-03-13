package at.robthered.simpletodo.features.core.domain.eventBus.events

import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityModel

sealed class PriorityModelEvent {
    data class NewPriorityModelEvent(val priorityModel: PriorityModel?): PriorityModelEvent()
    data class CurrentPriorityModelEvent(val currentPriorityModel: PriorityModel?): PriorityModelEvent()
    data object ClearEvent: PriorityModelEvent()
}