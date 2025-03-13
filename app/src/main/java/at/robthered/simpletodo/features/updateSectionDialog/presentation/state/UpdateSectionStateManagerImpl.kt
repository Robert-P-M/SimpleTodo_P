package at.robthered.simpletodo.features.updateSectionDialog.presentation.state

import at.robthered.simpletodo.features.core.domain.use_case.validation.ValidateSectionTitleUseCase
import at.robthered.simpletodo.features.core.domain.use_case.validation.utils.validateField
import at.robthered.simpletodo.features.dataSource.domain.repository.section.SectionRepository
import at.robthered.simpletodo.features.homeScreen.presentation.error.SectionModelError
import at.robthered.simpletodo.features.core.presentation.error.toUiText
import at.robthered.simpletodo.features.updateSectionDialog.domain.state.UpdateSectionState
import at.robthered.simpletodo.features.updateSectionDialog.domain.state.UpdateSectionStateAction
import at.robthered.simpletodo.features.updateSectionDialog.domain.state.UpdateSectionStateManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UpdateSectionStateManagerImpl(
    private val coroutineScope: CoroutineScope,
    private val validateSectionTitleUseCase: ValidateSectionTitleUseCase,
    private val sectionRepository: SectionRepository,
) : UpdateSectionStateManager {
    private val _state: MutableStateFlow<UpdateSectionState> =
        MutableStateFlow(UpdateSectionState())
    override val state: StateFlow<UpdateSectionState>
        get() = _state
            .stateIn(
                scope = coroutineScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = UpdateSectionState()
            )

    private fun onInitializeState(action: UpdateSectionStateAction.OnInitializeState) {
        _state.update {
            it.copy(
                updateSection = action.sectionModel,
            )
        }
    }


    private fun onChangeTitle(action: UpdateSectionStateAction.OnChangeTitle) {
        validateField(
            fieldValue = action.title,
            validateUseCase = { validateSectionTitleUseCase(it) }
        ) { error ->
            _state.update {
                it.copy(
                    updateSection = _state.value.updateSection.copy(
                        title = action.title
                    ),
                    sectionModelErrorState = it.sectionModelErrorState.copy(
                        titleError = error?.toUiText()
                    ),
                    canSave = error == null
                )
            }

        }
    }

    private fun onClearSection() {
        _state.update {
            it.copy(
                updateSection = it.updateSection.copy(
                    title = "",
                ),
                sectionModelErrorState = SectionModelError()
            )
        }
    }

    private fun onUpdateSection() = coroutineScope.launch {
        sectionRepository.upsert(item = _state.value.updateSection)
        onClearSection()
    }


    override fun handleAction(action: UpdateSectionStateAction) {
        when (action) {
            is UpdateSectionStateAction.OnInitializeState -> onInitializeState(action)
            UpdateSectionStateAction.OnDismiss -> onClearSection()
            is UpdateSectionStateAction.OnChangeTitle -> onChangeTitle(action)
            UpdateSectionStateAction.OnClearSection -> onClearSection()
            UpdateSectionStateAction.OnUpdateSection -> onUpdateSection()
        }
    }
}