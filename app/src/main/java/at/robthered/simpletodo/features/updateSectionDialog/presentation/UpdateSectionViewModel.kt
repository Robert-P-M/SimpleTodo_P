package at.robthered.simpletodo.features.updateSectionDialog.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import at.robthered.simpletodo.features.core.presentation.navigation.MainRoute
import at.robthered.simpletodo.features.dataSource.domain.local.model.SectionModel
import at.robthered.simpletodo.features.dataSource.domain.repository.section.SectionRepository
import at.robthered.simpletodo.features.updateSectionDialog.domain.state.UpdateSectionState
import at.robthered.simpletodo.features.updateSectionDialog.domain.state.UpdateSectionStateAction
import at.robthered.simpletodo.features.updateSectionDialog.domain.state.UpdateSectionStateManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UpdateSectionViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val updateSectionStateManager: UpdateSectionStateManager,
    private val sectionRepository: SectionRepository,
) : ViewModel() {

    private var loadSectionJob: Job? = null

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean>
        get() {
            return _isLoading
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    initialValue = false
                )
        }

    init {
        loadSectionJob?.cancel()
        loadSectionJob = viewModelScope.launch {
            delay(100)
            initializeData()
        }
    }


    private suspend fun initializeData() {
        _isLoading.update { true }
        val args = savedStateHandle.toRoute<MainRoute.UpdateSectionDialog>()
        sectionRepository.get(id = args.sectionId).collect { sectionModel: SectionModel? ->
            if (sectionModel != null) {

                updateSectionStateManager.handleAction(
                    UpdateSectionStateAction.OnInitializeState(
                        sectionModel = sectionModel
                    )
                )
            }
            _isLoading.update { false }
        }
    }

    val updateSectionState: StateFlow<UpdateSectionState> get() = updateSectionStateManager.state
    fun onUpdateSectionStateAction(action: UpdateSectionStateAction) {
        updateSectionStateManager.handleAction(action)
    }
}