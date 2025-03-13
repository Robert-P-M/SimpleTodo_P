package at.robthered.simpletodo.features.addSectionDialog.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.domain.Resource
import at.robthered.simpletodo.features.core.domain.state_manager.section.SectionStateAction
import at.robthered.simpletodo.features.core.domain.state_manager.section.SectionStateManager
import at.robthered.simpletodo.features.core.presentation.UiText
import at.robthered.simpletodo.features.core.domain.status.AddSectionStatus
import at.robthered.simpletodo.features.core.utils.onSuccess
import at.robthered.simpletodo.features.dataSource.domain.local.model.SectionModel
import at.robthered.simpletodo.features.dataSource.domain.use_case.section.SaveSectionUseCase
import at.robthered.simpletodo.features.homeScreen.presentation.error.SectionModelError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddSectionDialogViewModel(
    private val sectionStateManager: SectionStateManager,
    private val saveSectionUseCase: SaveSectionUseCase,
) : ViewModel() {

    private val _statusResource: MutableStateFlow<Resource<Unit>> = MutableStateFlow(Resource.Stale)
     val statusResource: StateFlow<Resource<Unit>>
        get() = _statusResource.asStateFlow()

    val sectionModel: StateFlow<SectionModel>
        get() = sectionStateManager.sectionModel
    val sectionModelError: StateFlow<SectionModelError>
        get() = sectionStateManager.sectionModelError
    val canSave: StateFlow<Boolean>
        get() = sectionStateManager.canSave
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = false
            )


    fun handleAddSectionDialogAction(action: AddSectionDialogScreenAction) {
        when (action) {
            is AddSectionDialogScreenAction.SectionAction -> sectionStateManager.handleAction(action.action)
            AddSectionDialogScreenAction.OnNavigateBack -> {
                sectionStateManager.handleAction(SectionStateAction.OnClearSection
                )
            }
            AddSectionDialogScreenAction.OnSaveSection -> onSaveSection()
        }
    }

    private fun onSaveSection() = viewModelScope.launch {
        _statusResource.update {
            Resource.Loading(loadingInfo = AddSectionStatus.SAVING)
        }
        try {
            saveSectionUseCase(sectionModel = sectionModel.value)
                .onSuccess {
                    _statusResource.update {
                        Resource.Success(Unit)
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

    init {
        /*val args = savedStateHandle.toRoute<MainRoute.AddSectionDialog>()*/
        sectionStateManager.handleAction(
            SectionStateAction
                .OnInitializeState(
                    SectionModel(
                        /*projectId = args.projectId*/
                    )
                )
        )
    }
}