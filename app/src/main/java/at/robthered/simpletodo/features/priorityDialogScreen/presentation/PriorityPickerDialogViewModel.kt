package at.robthered.simpletodo.features.priorityDialogScreen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.robthered.simpletodo.features.core.domain.eventBus.events.PriorityModelEvent
import at.robthered.simpletodo.features.core.domain.eventBus.events.PriorityModelEventBus
import at.robthered.simpletodo.features.core.domain.eventBus.on
import at.robthered.simpletodo.features.core.domain.state_manager.priority.PriorityStateManager
import at.robthered.simpletodo.features.core.domain.state_manager.priority.PriorityStateManagerAction
import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PriorityPickerDialogViewModel(
    private val priorityModelEventBus: PriorityModelEventBus,
    private val priorityStateManager: PriorityStateManager,
):ViewModel() {
    val priorityModel: StateFlow<PriorityModel>
        get() = priorityStateManager.priorityModel

    fun handleAction(action: PriorityPickerDialogAction){
        when (action) {
            is PriorityPickerDialogAction.PriorityStateAction -> priorityStateManager.handleAction(action.action)
            PriorityPickerDialogAction.OnNavigateBack -> Unit
            PriorityPickerDialogAction.OnSendPriorityModel ->
                viewModelScope.launch {
                    priorityModelEventBus
                        .publish(
                            event = PriorityModelEvent.NewPriorityModelEvent(
                                priorityModel = priorityModel.value
                            )
                        )
                }
        }
    }

    private val priorityModelEvents = priorityModelEventBus
        .on<PriorityModelEvent, PriorityModelEvent.CurrentPriorityModelEvent>(scope = viewModelScope) { event ->
            priorityStateManager.handleAction(
                PriorityStateManagerAction
                    .OnInitializeState(priorityModel = event.currentPriorityModel ?: PriorityModel())
            )
        }


}