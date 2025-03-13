package at.robthered.simpletodo.features.addTaskDialog.presentation

import at.robthered.simpletodo.features.core.domain.state_manager.event.EventStateManagerAction
import at.robthered.simpletodo.features.core.domain.state_manager.priority.PriorityStateManagerAction
import at.robthered.simpletodo.features.core.domain.state_manager.sharedUrls.SharedUrlsStateManagerAction
import at.robthered.simpletodo.features.core.domain.state_manager.task.TaskStateManagerAction

sealed interface AddTaskDialogAction {
    data class TaskAction(val action: TaskStateManagerAction) : AddTaskDialogAction
    data class PriorityAction(val action: PriorityStateManagerAction) : AddTaskDialogAction
    data class EventAction(val action: EventStateManagerAction) : AddTaskDialogAction
    data class SharedUrlAction(val action: SharedUrlsStateManagerAction) : AddTaskDialogAction
    data object OnSaveTask: AddTaskDialogAction
    data object OnNavigateBack: AddTaskDialogAction
    data object OnNavigateToDueDialog: AddTaskDialogAction
    data object OnNavigateToSharedUrlProcessorDialog: AddTaskDialogAction
    data object OnNavigateToPriorityDialog: AddTaskDialogAction
}