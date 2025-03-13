package at.robthered.simpletodo.features.core.data.state_manager.section

import at.robthered.simpletodo.features.core.domain.state_manager.section.SectionStateAction
import at.robthered.simpletodo.features.core.domain.state_manager.section.SectionStateManager
import at.robthered.simpletodo.features.core.domain.use_case.validation.ValidateSectionTitleUseCase
import at.robthered.simpletodo.features.core.domain.use_case.validation.utils.validateField
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.dataSource.domain.local.model.SectionModel
import at.robthered.simpletodo.features.homeScreen.presentation.error.SectionModelError
import at.robthered.simpletodo.features.core.presentation.error.toUiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update

class SectionStateManagerImpl(
    private val validateSectionTitleUseCase: ValidateSectionTitleUseCase,
) : SectionStateManager {
    private val _sectionModel: MutableStateFlow<SectionModel> = MutableStateFlow(SectionModel())
    override val sectionModel: StateFlow<SectionModel>
        get() = _sectionModel.asStateFlow()

    private val _sectionModelError: MutableStateFlow<SectionModelError> = MutableStateFlow(
        SectionModelError()
    )
    override val sectionModelError: StateFlow<SectionModelError>
        get() = _sectionModelError.asStateFlow()

    override val canSave: Flow<Boolean> = combine(
        _sectionModel,
        _sectionModelError
    ) { model, error ->
        updateCanSave(model, error)
    }

    private fun updateCanSave(
        model: SectionModel,
        error: SectionModelError
    ) = validateSectionTitleUseCase(model.title) is Result.Success && error.titleError == null

    private fun onInitializeState(action: SectionStateAction.OnInitializeState) {
        _sectionModel.update {
            action.sectionModel
        }
        _sectionModelError.update { SectionModelError() }
    }

    private fun onChangeTitle(action: SectionStateAction.OnChangeTitle) {
        validateField(
            fieldValue = action.title,
            validateUseCase = { validateSectionTitleUseCase(it) },
        ) { error ->
            _sectionModel.update {
                it.copy(
                    title = action.title
                )
            }
            _sectionModelError.update {
                it.copy(
                    titleError = error?.toUiText()
                )
            }
        }
    }


    private fun onClearState() {
        _sectionModel.value = SectionModel()
        _sectionModelError.value = SectionModelError()
    }


    override fun handleAction(action: SectionStateAction) {
        when (action) {
            is SectionStateAction.OnInitializeState -> onInitializeState(action)
            SectionStateAction.OnDismiss -> this.onClearState()
            is SectionStateAction.OnChangeTitle -> onChangeTitle(action)
            SectionStateAction.OnClearSection -> this.onClearState()
        }
    }
}