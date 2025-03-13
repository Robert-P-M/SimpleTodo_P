package at.robthered.simpletodo.features.core.data.state_manager.task

import at.robthered.simpletodo.features.core.domain.state_manager.task.TaskStateManager
import at.robthered.simpletodo.features.core.domain.state_manager.task.TaskStateManagerAction
import at.robthered.simpletodo.features.core.domain.use_case.validation.ValidateTaskDescriptionUseCase
import at.robthered.simpletodo.features.core.domain.use_case.validation.ValidateTaskTitleUseCase
import at.robthered.simpletodo.features.core.domain.use_case.validation.utils.validateField
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.dataSource.domain.local.model.TaskModel
import at.robthered.simpletodo.features.homeScreen.presentation.error.TaskModelError
import at.robthered.simpletodo.features.core.presentation.error.toUiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update

class TaskStateManagerImpl(
    private val validateTaskTitleUseCase: ValidateTaskTitleUseCase,
    private val validateTaskDescriptionUseCase: ValidateTaskDescriptionUseCase,
): TaskStateManager {
    private val _taskModel: MutableStateFlow<TaskModel> = MutableStateFlow(TaskModel())
    override val taskModel: StateFlow<TaskModel>
        get() = _taskModel.asStateFlow()

    private val _taskModelError: MutableStateFlow<TaskModelError> =
        MutableStateFlow(TaskModelError())
    override val taskModelError: StateFlow<TaskModelError>
        get() = _taskModelError.asStateFlow()


    override val canSave: Flow<Boolean> = combine(
        _taskModel,
        _taskModelError,
    ) { model, error ->
        updateCanSave(model, error)
    }


    private fun updateCanSave(
        model: TaskModel,
        error: TaskModelError,
    ) = validateTaskTitleUseCase(model.title) is Result.Success &&
            validateTaskDescriptionUseCase(model.description) is Result.Success &&
            error.titleError == null && error.descriptionError == null

    override fun handleAction(action: TaskStateManagerAction) {
        when (action) {
            is TaskStateManagerAction.OnChangeTitle -> onChangeTitle(action)
            is TaskStateManagerAction.OnChangeDescription -> onChangeDescription(action)
            TaskStateManagerAction.OnClearState -> onClearState()
            is TaskStateManagerAction.OnInitializeState -> onInitializeState(action)
        }
    }

    private fun onInitializeState(action: TaskStateManagerAction.OnInitializeState) {
        _taskModel.update {
            action.taskModel
        }
        _taskModelError.update {
            TaskModelError()
        }
    }

    private fun onClearState() {
        _taskModel.value = TaskModel()
        _taskModelError.value = TaskModelError()
    }

    private fun onChangeDescription(action: TaskStateManagerAction.OnChangeDescription) {
        validateField(
            fieldValue = action.description,
            validateUseCase = { validateTaskDescriptionUseCase(it) }
        ) { error ->
            _taskModel.update {
                it.copy(
                    description = action.description
                )
            }
            _taskModelError.update {
                it.copy(
                    descriptionError = error?.toUiText()
                )
            }
        }
    }

    private fun onChangeTitle(action: TaskStateManagerAction.OnChangeTitle) {
        validateField(
            fieldValue = action.title,
            validateUseCase = { validateTaskTitleUseCase(it) }
        ) { error ->
            _taskModel.update {
                it.copy(
                    title = action.title
                )
            }
            _taskModelError.update {
                it.copy(
                    titleError = error?.toUiText()
                )
            }
        }
    }
}