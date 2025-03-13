package at.robthered.simpletodo.features.taskDetailsDialog.presentation

import at.robthered.simpletodo.features.core.domain.state.expandedItemsState.ExpandedItemsStateAction
import at.robthered.simpletodo.features.core.domain.state_manager.completed.CompletedStateAction
import at.robthered.simpletodo.features.dataSource.domain.local.model.EventModel

sealed interface TaskDetailsDialogAction {
    data object OnNavigateToPriorityDialog: TaskDetailsDialogAction
    data object OnNavigateToSharedUrlProcessorDialog: TaskDetailsDialogAction
    data object OnNavigateBack: TaskDetailsDialogAction
    data object OnNavigateToDueDialog: TaskDetailsDialogAction
    data class OnNavigateToTaskDetailsRoute(val taskId: Long): TaskDetailsDialogAction
    data class OnNavigateToAddTaskRoute(val parentTaskId: Long): TaskDetailsDialogAction
    data class OnNavigateToTaskActivityLogDialog(val taskId: Long): TaskDetailsDialogAction
    data class OnNavigateToUpdateTaskDialog(val taskId: Long): TaskDetailsDialogAction
    data class OnRemoveLink(val linkId: Long?): TaskDetailsDialogAction
    data class OnDeleteTask(val taskId: Long?): TaskDetailsDialogAction
    data class CompleteAction(val action: CompletedStateAction) : TaskDetailsDialogAction
    data object OnToggleNotification: TaskDetailsDialogAction
}