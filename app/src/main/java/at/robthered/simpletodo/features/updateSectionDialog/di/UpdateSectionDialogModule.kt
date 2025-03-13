package at.robthered.simpletodo.features.updateSectionDialog.di

import androidx.lifecycle.SavedStateHandle
import at.robthered.simpletodo.features.core.domain.use_case.validation.ValidateSectionTitleUseCase
import at.robthered.simpletodo.features.dataSource.domain.repository.section.SectionRepository
import at.robthered.simpletodo.features.updateSectionDialog.domain.state.UpdateSectionStateManager
import at.robthered.simpletodo.features.updateSectionDialog.presentation.UpdateSectionViewModel
import at.robthered.simpletodo.features.updateSectionDialog.presentation.state.UpdateSectionStateManagerImpl
import kotlinx.coroutines.CoroutineScope
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val updateSectionDialogModule = module {

    factory<UpdateSectionStateManager> {
        UpdateSectionStateManagerImpl(
            coroutineScope = get<CoroutineScope>(),
            validateSectionTitleUseCase = get<ValidateSectionTitleUseCase>(),
            sectionRepository = get<SectionRepository>()
        )
    }

    viewModel {
        UpdateSectionViewModel(
            savedStateHandle = get<SavedStateHandle>(),
            updateSectionStateManager = get<UpdateSectionStateManager>(),
            sectionRepository = get<SectionRepository>()
        )
    }
}