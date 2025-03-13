package at.robthered.simpletodo.features.core.data.state_manager.priority

import at.robthered.simpletodo.features.core.domain.eventBus.events.PriorityModelEventBus
import at.robthered.simpletodo.features.core.domain.state_manager.priority.PriorityStateManagerAction
import at.robthered.simpletodo.features.core.domain.state_manager.priority.PriorityStateManager
import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PriorityStateManagerImpl : PriorityStateManager {
    private val _priorityModel: MutableStateFlow<PriorityModel> = MutableStateFlow(PriorityModel())
    override val priorityModel: StateFlow<PriorityModel>
        get() = _priorityModel.asStateFlow()

    override fun handleAction(action: PriorityStateManagerAction) {
        when (action) {
            is PriorityStateManagerAction.OnChangePriority -> onChangePriority(action)
            PriorityStateManagerAction.OnClearState -> onClearState()
            is PriorityStateManagerAction.OnInitializeState -> onInitializeState(action)
        }
    }

    private fun onInitializeState(action: PriorityStateManagerAction.OnInitializeState) {
        _priorityModel.update {
            action.priorityModel
        }
    }

    private fun onClearState() {
        _priorityModel.value = PriorityModel()
    }

    private fun onChangePriority(action: PriorityStateManagerAction.OnChangePriority) {
        _priorityModel.update {
            it.copy(
                priority = action.priorityEnum
            )
        }
    }
}