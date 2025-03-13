package at.robthered.simpletodo.features.updateTaskDialog.presentation

import at.robthered.simpletodo.features.core.domain.state_manager.event.EventStateManagerAction
import at.robthered.simpletodo.features.core.domain.state_manager.priority.PriorityStateManagerAction
import at.robthered.simpletodo.features.core.domain.state_manager.task.TaskStateManagerAction

sealed interface UpdateTaskDialogAction {
    data object OnNavigateToDueDialog: UpdateTaskDialogAction
    data object OnNavigateToPriorityDialog: UpdateTaskDialogAction
    data object OnNavigateBack: UpdateTaskDialogAction
    data object OnNavigateToSharedUrlProcessorDialog: UpdateTaskDialogAction
    data object OnToggleNotification: UpdateTaskDialogAction
    data object OnUpdateTask: UpdateTaskDialogAction
    data class OnRemoveLink(val linkId: Long?): UpdateTaskDialogAction
    data class TaskAction(val action: TaskStateManagerAction): UpdateTaskDialogAction
    data class PriorityAction(val action: PriorityStateManagerAction): UpdateTaskDialogAction
    data class EventAction(val action: EventStateManagerAction): UpdateTaskDialogAction
}