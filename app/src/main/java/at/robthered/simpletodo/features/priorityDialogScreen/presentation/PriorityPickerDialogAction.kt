package at.robthered.simpletodo.features.priorityDialogScreen.presentation

import at.robthered.simpletodo.features.core.domain.state_manager.priority.PriorityStateManagerAction

sealed interface PriorityPickerDialogAction {
    data object OnSendPriorityModel: PriorityPickerDialogAction
    data object OnNavigateBack: PriorityPickerDialogAction
    data class PriorityStateAction(val action: PriorityStateManagerAction):
        PriorityPickerDialogAction
}