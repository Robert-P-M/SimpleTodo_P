package at.robthered.simpletodo.features.taskActivityProtocolDialog.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import at.robthered.simpletodo.features.core.domain.Resource
import at.robthered.simpletodo.features.core.presentation.navigation.MainRoute
import at.robthered.simpletodo.features.taskActivityProtocolDialog.domain.use_case.TaskActivityUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class TaskActivityDialogViewModel(
    taskActivityUseCase: TaskActivityUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    val activities = taskActivityUseCase(taskId = savedStateHandle.toRoute<MainRoute.TaskActivityLogDialog>().taskId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Resource.Loading()
        )
}