package at.robthered.simpletodo.features.homeScreen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.robthered.simpletodo.features.core.domain.Resource
import at.robthered.simpletodo.features.core.domain.state.expandedItemsState.ExpandedItemsState
import at.robthered.simpletodo.features.core.domain.state.expandedItemsState.ExpandedItemsStateAction
import at.robthered.simpletodo.features.core.domain.state.expandedItemsState.ExpandedItemsStateManager
import at.robthered.simpletodo.features.dataSource.domain.use_case.section.DeleteSectionUseCase
import at.robthered.simpletodo.features.dataSource.domain.use_case.task.DeleteTaskUseCase
import at.robthered.simpletodo.features.homeScreen.domain.model.ScreenItemsModel
import at.robthered.simpletodo.features.core.domain.state_manager.archived.ArchivedStateAction
import at.robthered.simpletodo.features.core.domain.state_manager.archived.ArchivedStateManager
import at.robthered.simpletodo.features.core.domain.state_manager.completed.CompletedStateAction
import at.robthered.simpletodo.features.core.domain.state_manager.completed.CompletedStateManager
import at.robthered.simpletodo.features.homeScreen.domain.use_case.LoadHomeItemsUseCase
import at.robthered.simpletodo.features.shared.locale.domain.state.AppLocaleState
import at.robthered.simpletodo.features.shared.locale.domain.state.AppLocaleStateAction
import at.robthered.simpletodo.features.shared.locale.domain.state.AppLocaleStateManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val completedStateManager: CompletedStateManager,
    private val archivedStateManager: ArchivedStateManager,
    private val expandedTasksStateManager: ExpandedItemsStateManager,
    private val expandedSectionsStateManager: ExpandedItemsStateManager,
    private val appLocaleStateManager: AppLocaleStateManager,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val deleteSectionUseCase: DeleteSectionUseCase,
    private val loadHomeItemsUseCase: LoadHomeItemsUseCase,
) : ViewModel() {

    fun handleAction(action: HomeScreenAction) {
        when (action) {
            is HomeScreenAction.OnDeleteSection -> onDeleteSection(action)
            is HomeScreenAction.OnDeleteTask -> onDeleteTask(action)
        }
    }
    private fun onDeleteTask(action: HomeScreenAction.OnDeleteTask) {
        viewModelScope.launch {
            deleteTaskUseCase(taskId = action.taskId)
        }
    }

    private fun onDeleteSection(action: HomeScreenAction.OnDeleteSection) {
        viewModelScope.launch {
            deleteSectionUseCase(sectionId = action.sectionId)
        }
    }

    val appLocaleState: StateFlow<AppLocaleState> get() = appLocaleStateManager.state
    fun onAppLocaleStateAction(action: AppLocaleStateAction) {
        appLocaleStateManager.handleAction(action)
    }

    val expandedTasksState: StateFlow<ExpandedItemsState> get() = expandedTasksStateManager.state
    fun onExpandedTasksStateAction(action: ExpandedItemsStateAction) {
        expandedTasksStateManager.handleAction(action)
    }

    val expandedSectionsState: StateFlow<ExpandedItemsState> get() = expandedSectionsStateManager.state
    fun onExpandedSectionsStateAction(action: ExpandedItemsStateAction) {
        expandedSectionsStateManager.handleAction(action)
    }


    val homeItemsResource: StateFlow<Resource<List<ScreenItemsModel>>>
        get() = loadHomeItemsUseCase(depth = 5)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = Resource.Loading()
            )

    fun onCompletedStateAction(action: CompletedStateAction) {
        completedStateManager.handleAction(action)
    }

    fun onArchivedStateAction(action: ArchivedStateAction) {
        archivedStateManager.handleAction(action)
    }

}