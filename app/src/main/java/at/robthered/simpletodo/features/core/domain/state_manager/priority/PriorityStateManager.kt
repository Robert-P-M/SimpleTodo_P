package at.robthered.simpletodo.features.core.domain.state_manager.priority

import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityModel
import kotlinx.coroutines.flow.StateFlow

interface PriorityStateManager {
    val priorityModel: StateFlow<PriorityModel>
    fun handleAction(action: PriorityStateManagerAction)
}