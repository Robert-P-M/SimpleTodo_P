package at.robthered.simpletodo.features.core.domain.state_manager.event

import at.robthered.simpletodo.features.dataSource.domain.local.model.EventModel
import kotlinx.coroutines.flow.StateFlow

interface EventStateManager {
    val eventModel: StateFlow<EventModel?>
    fun handleAction(action: EventStateManagerAction)
}