package at.robthered.simpletodo.features.addSectionDialog.di

import at.robthered.simpletodo.features.addSectionDialog.presentation.AddSectionDialogViewModel
import at.robthered.simpletodo.features.core.domain.state_manager.section.SectionStateManager
import at.robthered.simpletodo.features.dataSource.domain.use_case.section.SaveSectionUseCase
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val addSectionDialogModule = module {

    viewModel {
        AddSectionDialogViewModel(
            sectionStateManager = get<SectionStateManager>(),
            saveSectionUseCase = get<SaveSectionUseCase>(),
        )
    }
}