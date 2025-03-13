package at.robthered.simpletodo.features.taskDetailsDialog.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import at.robthered.simpletodo.features.core.domain.Resource
import at.robthered.simpletodo.features.core.domain.eventBus.clear
import at.robthered.simpletodo.features.core.domain.eventBus.events.DueEvent
import at.robthered.simpletodo.features.core.domain.eventBus.events.DueEventBus
import at.robthered.simpletodo.features.core.domain.eventBus.events.PriorityModelEvent
import at.robthered.simpletodo.features.core.domain.eventBus.events.PriorityModelEventBus
import at.robthered.simpletodo.features.core.domain.eventBus.events.SharedUrlModelEvent
import at.robthered.simpletodo.features.core.domain.eventBus.events.SharedUrlModelEventBus
import at.robthered.simpletodo.features.core.domain.eventBus.on
import at.robthered.simpletodo.features.core.domain.state_manager.completed.CompletedStateAction
import at.robthered.simpletodo.features.core.domain.state_manager.completed.CompletedStateManager
import at.robthered.simpletodo.features.core.presentation.navigation.MainRoute
import at.robthered.simpletodo.features.core.utils.onSuccess
import at.robthered.simpletodo.features.dataSource.domain.local.model.EventModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityModel
import at.robthered.simpletodo.features.dataSource.domain.use_case.event.UpdateTaskEventUseCase
import at.robthered.simpletodo.features.dataSource.domain.use_case.link.RemoveLinkUseCase
import at.robthered.simpletodo.features.dataSource.domain.use_case.link.SaveLinkToTaskUseCase
import at.robthered.simpletodo.features.dataSource.domain.use_case.priority.CreatePriorityUseCase
import at.robthered.simpletodo.features.dataSource.domain.use_case.task.DeleteTaskUseCase
import at.robthered.simpletodo.features.dataSource.domain.use_case.task.LoadTaskUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class TaskDetailsDialogViewModel(
    savedStateHandle: SavedStateHandle,
    loadTaskUseCase: LoadTaskUseCase,
    private val completedStateManager: CompletedStateManager,
    private val dueEventBus: DueEventBus,
    private val updateTaskEventUseCase: UpdateTaskEventUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val shareUrlModelEventBus: SharedUrlModelEventBus,
    private val priorityModelEventBus: PriorityModelEventBus,
    private val saveLinkToTaskUseCase: SaveLinkToTaskUseCase,
    private val removeLinkUseCase: RemoveLinkUseCase,
    private val createPriorityUseCase: CreatePriorityUseCase,
) : ViewModel() {
    val taskId = savedStateHandle.toRoute<MainRoute.TaskDetailsDialog>().taskId

    private val priorityModel: MutableStateFlow<PriorityModel> = MutableStateFlow(PriorityModel())
    private val eventModel: MutableStateFlow<EventModel?> = MutableStateFlow(null)

    fun handleAction(action: TaskDetailsDialogAction) {
        when (action) {
            TaskDetailsDialogAction.OnNavigateToPriorityDialog -> onNavigateToPriorityDialog()
            TaskDetailsDialogAction.OnNavigateBack -> Unit
            TaskDetailsDialogAction.OnNavigateToSharedUrlProcessorDialog -> Unit
            is TaskDetailsDialogAction.OnNavigateToTaskActivityLogDialog -> Unit
            is TaskDetailsDialogAction.OnNavigateToUpdateTaskDialog -> Unit
            is TaskDetailsDialogAction.OnRemoveLink -> onRemoveLink(action)
            is TaskDetailsDialogAction.OnDeleteTask -> onDeleteTask(action)
            is TaskDetailsDialogAction.CompleteAction -> onCompletedStateAction(action.action)
            TaskDetailsDialogAction.OnNavigateToDueDialog -> onSetTaskDateEvent(eventModel.value)
            is TaskDetailsDialogAction.OnNavigateToTaskDetailsRoute -> Unit
            is TaskDetailsDialogAction.OnNavigateToAddTaskRoute -> Unit
            TaskDetailsDialogAction.OnToggleNotification -> onToggleNotification()

        }
    }

    private fun onToggleNotification() {
        eventModel.value?.let { event ->
            viewModelScope.launch {
                updateTaskEventUseCase(
                    eventModel = event.copy(isNotificationEnabled = event.isNotificationEnabled.not()),
                    taskId = taskId
                )
            }
        }
    }

    private fun onDeleteTask(action: TaskDetailsDialogAction.OnDeleteTask) = viewModelScope.launch {
        deleteTaskUseCase(taskId = action.taskId)
    }

    val taskDetailsResource =
        loadTaskUseCase(
            taskId = taskId,
            depth = 4
        )
            .distinctUntilChanged()
            .onEach {
                (it as? Resource.Success)?.data?.let { taskWithDetailsAndChildrenModel ->
                    priorityModel.update {
                        taskWithDetailsAndChildrenModel.priority.maxByOrNull { model -> model.createdAt } ?: PriorityModel()
                    }
                    eventModel.update {
                        taskWithDetailsAndChildrenModel.event
                    }
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = Resource.Loading()
            )

    private val _events = dueEventBus.on<DueEvent,DueEvent.NewEventModel>(
        scope = viewModelScope
    ) { event ->
        updateTaskEventUseCase(eventModel = event.eventModel, taskId = taskId)
    }
    private val shareUrlModelEvents = shareUrlModelEventBus
        .on<SharedUrlModelEvent, SharedUrlModelEvent.NewSharedUrlModelEvent>(scope = viewModelScope) { event ->
            saveLinkToTaskUseCase(sharedUrlModel = event.sharedUrlModel.copy(taskId = taskId))
                .onSuccess {
                    shareUrlModelEventBus.clear(
                        defaultEvent = SharedUrlModelEvent.ClearEvent
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
    private val priorityModelEvents = priorityModelEventBus
        .on<PriorityModelEvent, PriorityModelEvent.NewPriorityModelEvent>(scope = viewModelScope) { event ->
            if(priorityModel.value.priority != event.priorityModel?.priority && event.priorityModel != null){
                createPriorityUseCase(
                    priorityModel = event.priorityModel
                )

            }
            priorityModelEventBus.clear(PriorityModelEvent.ClearEvent)
        }


    private fun onSetTaskDateEvent(taskEvent: EventModel?) {
        viewModelScope.launch {
            dueEventBus.publish(
                DueEvent.CurrentEventModel(
                    taskEvent
                )
            )
        }
    }

    private fun onRemoveLink(action: TaskDetailsDialogAction.OnRemoveLink) = viewModelScope.launch {
        removeLinkUseCase(linkId = action.linkId)
    }

    private fun onCompletedStateAction(action: CompletedStateAction) {
        completedStateManager.handleAction(action)
    }

}