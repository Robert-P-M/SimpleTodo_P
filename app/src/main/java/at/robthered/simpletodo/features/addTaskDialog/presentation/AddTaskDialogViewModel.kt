package at.robthered.simpletodo.features.addTaskDialog.presentation


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.domain.Resource
import at.robthered.simpletodo.features.core.domain.error.ImageError
import at.robthered.simpletodo.features.core.domain.eventBus.clear
import at.robthered.simpletodo.features.core.domain.eventBus.events.PriorityModelEvent
import at.robthered.simpletodo.features.core.domain.eventBus.events.PriorityModelEventBus
import at.robthered.simpletodo.features.core.domain.eventBus.on
import at.robthered.simpletodo.features.core.domain.state_manager.event.EventStateManager
import at.robthered.simpletodo.features.core.domain.state_manager.event.EventStateManagerAction
import at.robthered.simpletodo.features.core.domain.state_manager.priority.PriorityStateManager
import at.robthered.simpletodo.features.core.domain.state_manager.priority.PriorityStateManagerAction
import at.robthered.simpletodo.features.core.domain.state_manager.sharedUrls.SharedUrlsStateManager
import at.robthered.simpletodo.features.core.domain.state_manager.sharedUrls.SharedUrlsStateManagerAction
import at.robthered.simpletodo.features.core.domain.state_manager.task.TaskStateManager
import at.robthered.simpletodo.features.core.domain.state_manager.task.TaskStateManagerAction
import at.robthered.simpletodo.features.core.domain.status.AddTaskStatus
import at.robthered.simpletodo.features.core.domain.status.SaveImageStatus
import at.robthered.simpletodo.features.core.presentation.UiText
import at.robthered.simpletodo.features.core.presentation.error.toUiText
import at.robthered.simpletodo.features.core.presentation.navigation.MainRoute
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.core.utils.onError
import at.robthered.simpletodo.features.core.utils.onSuccess
import at.robthered.simpletodo.features.dataSource.domain.local.model.LinkModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.TaskModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.emptyEventModel
import at.robthered.simpletodo.features.dataSource.domain.use_case.taskWithDetails.SaveTaskWithDetailsUseCase
import at.robthered.simpletodo.features.homeScreen.presentation.error.TaskModelError
import at.robthered.simpletodo.features.network.domain.model.SharedUrlModel
import at.robthered.simpletodo.features.shared.image.domain.use_case.TransformSharedUrlToLinkModelUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddTaskDialogViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val taskStateManager: TaskStateManager,
    private val priorityStateManager: PriorityStateManager,
    private val sharedUrlsStateManager: SharedUrlsStateManager,
    private val eventStateManager: EventStateManager,
    private val saveTaskWithDetailsUseCase: SaveTaskWithDetailsUseCase,
    private val transformSharedUrlToLinkModelUseCase: TransformSharedUrlToLinkModelUseCase,
    private val priorityModelEventBus: PriorityModelEventBus,
) : ViewModel() {

    private val _statusResource: MutableStateFlow<Resource<Unit>> = MutableStateFlow(Resource.Stale)
    val statusResource: StateFlow<Resource<Unit>>
        get() = _statusResource.asStateFlow()

    private fun onSaveTask() = viewModelScope.launch {
        _statusResource.update {
            Resource.Loading(loadingInfo = AddTaskStatus.SAVING)
        }
        try {
            val linkModels = mutableListOf<LinkModel>()
            for(sharedUrlModel in sharedUrlModels.value) {
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
            saveTaskWithDetailsUseCase(
                taskModel = taskModel.value,
                priorityModel = priorityModel.value,
                eventModel = eventModel.value,
                linkModels = linkModels
            ).onSuccess {
                _statusResource.update {
                    Resource.Loading(loadingInfo = AddTaskStatus.DONE)
                }
                _statusResource.update {
                    Resource.Success(Unit)
                }
            }.onError { error ->
                _statusResource.update {
                    Resource.Error(error = error.toUiText())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _statusResource.update {
                Resource.Error(
                    error = UiText.StringResourceId(id = R.string.error_unknown)
                )
            }
        }
    }



    fun handleAction(action: AddTaskDialogAction){
        when (action) {
            is AddTaskDialogAction.EventAction -> eventStateManager.handleAction(action.action)
            is AddTaskDialogAction.PriorityAction -> priorityStateManager.handleAction(action.action)
            is AddTaskDialogAction.SharedUrlAction -> handleSharedUrlStateManagerAction(action.action)
            is AddTaskDialogAction.TaskAction -> taskStateManager.handleAction(action.action)
            AddTaskDialogAction.OnNavigateBack -> {
                eventStateManager.handleAction(
                    EventStateManagerAction.OnResetEvent
                )
            }
            AddTaskDialogAction.OnSaveTask -> onSaveTask()
            AddTaskDialogAction.OnNavigateToDueDialog -> {
                eventStateManager.handleAction(
                    EventStateManagerAction.OnSendCurrentEvent
                )
            }
            AddTaskDialogAction.OnNavigateToPriorityDialog -> {
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
            AddTaskDialogAction.OnNavigateToSharedUrlProcessorDialog -> Unit
        }
    }


    val taskModel: StateFlow<TaskModel> = taskStateManager.taskModel
    val taskModelError: StateFlow<TaskModelError> = taskStateManager.taskModelError

    val priorityModel = priorityStateManager.priorityModel

    val sharedUrlModels: StateFlow<List<SharedUrlModel>> = sharedUrlsStateManager.sharedUrlModels
    private fun handleSharedUrlStateManagerAction(action: SharedUrlsStateManagerAction) = sharedUrlsStateManager.handleAction(action)

    private val priorityModelEvents = priorityModelEventBus
        .on<PriorityModelEvent, PriorityModelEvent.NewPriorityModelEvent>(scope = viewModelScope) { event ->

            priorityStateManager.handleAction(
                PriorityStateManagerAction
                    .OnInitializeState(priorityModel = event.priorityModel ?: PriorityModel())
            )
            priorityModelEventBus.clear(PriorityModelEvent.ClearEvent)
        }


    val eventModel = eventStateManager.eventModel

    val canSave: StateFlow<Boolean> = taskStateManager.canSave
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )




    private fun initializeStates() {
        val args = savedStateHandle.toRoute<MainRoute.AddTaskDialog>()
        taskStateManager.handleAction(
            TaskStateManagerAction
                .OnInitializeState(
                    taskModel = TaskModel(
                        sectionId = args.taskOfSectionId,
                        parentTaskId = args.parentTaskId,
                    )
                )
        )
        priorityStateManager.handleAction(
            PriorityStateManagerAction
                .OnInitializeState(
                    priorityModel = PriorityModel(
                        priority = args.priority
                    )
                )
        )
        eventStateManager.handleAction(
            EventStateManagerAction
                .OnUpdateEventModel(
                    eventModel = emptyEventModel
                )
        )
    }

    init {
        initializeStates()
    }
}