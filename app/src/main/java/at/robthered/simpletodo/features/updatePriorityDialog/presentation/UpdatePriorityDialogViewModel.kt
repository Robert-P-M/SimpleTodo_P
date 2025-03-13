package at.robthered.simpletodo.features.updatePriorityDialog.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import at.robthered.simpletodo.features.core.presentation.navigation.MainRoute
import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityEnum
import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityModel
import at.robthered.simpletodo.features.dataSource.domain.repository.priority.PriorityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UpdatePriorityDialogViewModel(
    savedStateHandle: SavedStateHandle,
    private val priorityRepository: PriorityRepository,
) : ViewModel() {

    private val _currentPriority = MutableStateFlow<PriorityEnum?>(null)
    val currentPriority: StateFlow<PriorityEnum?>
        get() = _currentPriority
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null
            )
    private val _taskId = MutableStateFlow<Long?>(null)
    
    fun onPickPriority(priorityEnum: PriorityEnum?) {
        _currentPriority.update { priorityEnum }
        viewModelScope.launch {
            priorityRepository.upsert(
                priorityModel = PriorityModel(
                    taskId = _taskId.value,
                    priority = priorityEnum
                )
            )
        }
    }

    init {
        val args = savedStateHandle.toRoute<MainRoute.UpdatePriorityDialog>()
        _currentPriority.update { args.currentPriority }
        _taskId.update { args.taskId }
    }
}