package at.robthered.simpletodo.features.updateTaskDialog.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.domain.Resource
import at.robthered.simpletodo.features.core.domain.error.ImageError
import at.robthered.simpletodo.features.core.domain.eventBus.clear
import at.robthered.simpletodo.features.core.domain.eventBus.events.DueEvent
import at.robthered.simpletodo.features.core.domain.eventBus.events.DueEventBus
import at.robthered.simpletodo.features.core.domain.eventBus.events.PriorityModelEvent
import at.robthered.simpletodo.features.core.domain.eventBus.events.PriorityModelEventBus
import at.robthered.simpletodo.features.core.domain.eventBus.events.SharedUrlModelEvent
import at.robthered.simpletodo.features.core.domain.eventBus.events.SharedUrlModelEventBus
import at.robthered.simpletodo.features.core.domain.eventBus.on
import at.robthered.simpletodo.features.core.domain.state_manager.event.EventStateManager
import at.robthered.simpletodo.features.core.domain.state_manager.event.EventStateManagerAction
import at.robthered.simpletodo.features.core.domain.state_manager.priority.PriorityStateManager
import at.robthered.simpletodo.features.core.domain.state_manager.priority.PriorityStateManagerAction
import at.robthered.simpletodo.features.core.domain.state_manager.task.TaskStateManager
import at.robthered.simpletodo.features.core.domain.state_manager.task.TaskStateManagerAction
import at.robthered.simpletodo.features.core.domain.status.SaveImageStatus
import at.robthered.simpletodo.features.core.domain.status.UpdateTaskStatus
import at.robthered.simpletodo.features.core.presentation.UiText
import at.robthered.simpletodo.features.core.presentation.error.toUiText
import at.robthered.simpletodo.features.core.presentation.navigation.MainRoute
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.core.utils.onError
import at.robthered.simpletodo.features.core.utils.onSuccess
import at.robthered.simpletodo.features.dataSource.domain.local.model.LinkModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.TaskModel
import at.robthered.simpletodo.features.dataSource.domain.local.relation.TaskWithDetailsAndChildrenModel
import at.robthered.simpletodo.features.dataSource.domain.use_case.UpdateTaskWithDetailsUseCase
import at.robthered.simpletodo.features.dataSource.domain.use_case.link.RemoveLinkUseCase
import at.robthered.simpletodo.features.dataSource.domain.use_case.task.LoadTaskUseCase
import at.robthered.simpletodo.features.homeScreen.presentation.error.TaskModelError
import at.robthered.simpletodo.features.network.domain.model.SharedUrlModel
import at.robthered.simpletodo.features.shared.image.domain.use_case.TransformSharedUrlToLinkModelUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UpdateTaskDialogViewModel(
    savedStateHandle: SavedStateHandle,
    private val taskStateManager: TaskStateManager,
    private val priorityStateManager: PriorityStateManager,
    private val eventStateManager: EventStateManager,
    private val dueEventBus: DueEventBus,
    loadTaskUseCase: LoadTaskUseCase,
    shareUrlModelEventBus: SharedUrlModelEventBus,
    private val removeLinkUseCase: RemoveLinkUseCase,
    private val priorityModelEventBus: PriorityModelEventBus,
    private val transformSharedUrlToLinkModelUseCase: TransformSharedUrlToLinkModelUseCase,
    private val updateTaskWithDetailsUseCase: UpdateTaskWithDetailsUseCase,
) : ViewModel() {

    private val taskId = savedStateHandle.toRoute<MainRoute.UpdateTaskDialog>().taskId

    val loadingResource: StateFlow<Resource<TaskWithDetailsAndChildrenModel>> = loadTaskUseCase(taskId = taskId)
            .onEach {

                (it as? Resource.Success)?.data?.let { data ->
                    taskStateManager.handleAction(
                        TaskStateManagerAction
                            .OnInitializeState(
                                taskModel = data.task
                            )
                    )
                    priorityStateManager.handleAction(
                        PriorityStateManagerAction
                            .OnInitializeState(
                                priorityModel = data.priority.maxByOrNull { model -> model.createdAt } ?: PriorityModel()
                            )
                    )
                    eventStateManager.handleAction(
                        EventStateManagerAction
                            .OnInitializeState(
                                eventModel = data.event
                            )
                    )

                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = Resource.Stale
            )

    private val _statusResource: MutableStateFlow<Resource<Unit>> = MutableStateFlow(Resource.Stale)
    val statusResource: StateFlow<Resource<Unit>>
        get() = _statusResource.asStateFlow()


    val taskModel: StateFlow<TaskModel> = taskStateManager.taskModel
    val taskModelError: StateFlow<TaskModelError> = taskStateManager.taskModelError


    val canSave: StateFlow<Boolean> = taskStateManager.canSave
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    val priorityModel = priorityStateManager.priorityModel

    val eventModel = eventStateManager.eventModel

    private val _links = MutableStateFlow<List<LinkModel>>(emptyList())
    val links: StateFlow<List<LinkModel>>
        get() = _links.asStateFlow()

    private val _newLinks = MutableStateFlow<List<SharedUrlModel>>(emptyList())
    val newLinks: StateFlow<List<SharedUrlModel>>
        get() = _newLinks.asStateFlow()


    fun handleAction(action: UpdateTaskDialogAction) {
        when (action) {
            UpdateTaskDialogAction.OnNavigateBack -> Unit
            UpdateTaskDialogAction.OnUpdateTask -> onUpdateTask()
            UpdateTaskDialogAction.OnNavigateToSharedUrlProcessorDialog -> Unit
            is UpdateTaskDialogAction.TaskAction -> taskStateManager.handleAction(action.action)
            is UpdateTaskDialogAction.EventAction -> eventStateManager.handleAction(action.action)
            is UpdateTaskDialogAction.PriorityAction -> priorityStateManager.handleAction(action.action)
            UpdateTaskDialogAction.OnToggleNotification -> onToggleNotification()
            UpdateTaskDialogAction.OnNavigateToPriorityDialog -> onNavigateToPriorityDialog()
            UpdateTaskDialogAction.OnNavigateToDueDialog -> onNavigateToDueDialog()
            is UpdateTaskDialogAction.OnRemoveLink -> onRemoveLink(action)

        }
    }

    private fun onToggleNotification() {
        eventModel.value?.let { model ->
            eventStateManager.handleAction(
                EventStateManagerAction
                    .OnInitializeState(
                        eventModel = model.copy(
                            isNotificationEnabled = model.isNotificationEnabled.not()
                        )
                    )
            )
        }
    }

    private fun onNavigateToPriorityDialog() {
        viewModelScope.launch {
            priorityModelEventBus
                .publish(
                    PriorityModelEvent
                        .CurrentPriorityModelEvent(
                            currentPriorityModel = priorityModel.value
                        )
                )
        }
    }

    private fun onNavigateToDueDialog() {
        viewModelScope.launch {
            dueEventBus.publish(
                DueEvent.CurrentEventModel(eventModel = eventModel.value)
            )
        }
    }

    private fun onRemoveLink(action: UpdateTaskDialogAction.OnRemoveLink) {
        viewModelScope.launch {
            removeLinkUseCase(linkId = action.linkId)
        }
    }

    private fun onUpdateTask() = viewModelScope.launch {
        _statusResource.update {
            Resource.Loading(loadingInfo = UpdateTaskStatus.SAVING)
        }

        try {
            _statusResource.update {
                Resource.Loading(loadingInfo = SaveImageStatus.SAVING)
            }
            val linkModels = mutableListOf<LinkModel>()
            for(sharedUrlModel in _newLinks.value) {
                _statusResource.update {
                    Resource.Loading(loadingInfo = SaveImageStatus.SAVING)
                }
                when(val linkModelResult = transformSharedUrlToLinkModelUseCase(sharedUrlModel)) {
                    is Result.Error -> {
                        Resource.Error(error = ImageError.Storage.FILE_CREATION_FAILED.toUiText())
                        return@launch
                    }
                    is Result.Success -> {
                        linkModels.add(linkModelResult.data)
                        _statusResource.update {
                            Resource.Loading(loadingInfo = SaveImageStatus.DONE)
                        }
                    }
                }

            }
            updateTaskWithDetailsUseCase(
                taskModel = taskModel.value,
                priorityModel = priorityModel.value,
                eventModel = eventModel.value,
                linkModels = linkModels
            ).onSuccess {
                _statusResource.update {
                    Resource.Success(Unit)
                }
            }.onError { error ->
                _statusResource.update {
                    Resource.Error(error = error.toUiText())
                }
            }

        }catch (e: Exception) {
            e.printStackTrace()
            _statusResource.update {
                Resource.Error(
                    error = UiText.StringResourceId(id = R.string.error_unknown)
                )
            }
        }
    }

    // TODO: update PRiorityStateManager to handle "eventBus" directly
    private val priorityModelEvents = priorityModelEventBus
        .on<PriorityModelEvent, PriorityModelEvent.NewPriorityModelEvent>(scope = viewModelScope) { event ->
            if(priorityModel.value.priority != event.priorityModel?.priority) {
                priorityStateManager.handleAction(
                    PriorityStateManagerAction
                        .OnInitializeState(
                            priorityModel = event.priorityModel?.copy(taskId = null) ?: PriorityModel()
                        )
                )
            }
            priorityModelEventBus.clear(PriorityModelEvent.ClearEvent)
        }


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean>
        get() = _isLoading
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = false
            )


    // TODO: update sharedUrlStateManager to handle "eventBus" directly
    private val shareUrlModelEvents = shareUrlModelEventBus
        .on<SharedUrlModelEvent, SharedUrlModelEvent.NewSharedUrlModelEvent>(scope = viewModelScope) { event ->
            _newLinks.update {
                newLinks.value + event.sharedUrlModel
            }
        }


}