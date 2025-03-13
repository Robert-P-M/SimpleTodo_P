package at.robthered.simpletodo.features.core.domain.state_manager.priority

import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityEnum
import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityModel

sealed interface PriorityStateManagerAction {
    data class OnChangePriority(val priorityEnum: PriorityEnum?): PriorityStateManagerAction
    data class OnInitializeState(val priorityModel: PriorityModel): PriorityStateManagerAction
    data object OnClearState: PriorityStateManagerAction
}