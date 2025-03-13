package at.robthered.simpletodo.features.core.domain.state_manager.task

import at.robthered.simpletodo.features.dataSource.domain.local.model.TaskModel
import at.robthered.simpletodo.features.homeScreen.presentation.error.TaskModelError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface TaskStateManager {
    val taskModel: StateFlow<TaskModel>
    val taskModelError: StateFlow<TaskModelError>
    val canSave: Flow<Boolean>
    fun handleAction(action: TaskStateManagerAction)
}