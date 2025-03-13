package at.robthered.simpletodo.features.core.domain.state_manager.dueEvent

import kotlinx.coroutines.flow.StateFlow

interface DueEventStateManager {
    val state: StateFlow<EventState>
    fun handleAction(action: DueEventStateManagerAction)
}