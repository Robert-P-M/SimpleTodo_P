package at.robthered.simpletodo.features.core.domain.state_manager.task

import at.robthered.simpletodo.features.dataSource.domain.local.model.TaskModel

sealed interface TaskStateManagerAction {
    data class OnChangeTitle(val title: String): TaskStateManagerAction
    data class OnChangeDescription(val description: String): TaskStateManagerAction
    data object OnClearState: TaskStateManagerAction
    data class OnInitializeState(
        val taskModel: TaskModel
    ): TaskStateManagerAction
}