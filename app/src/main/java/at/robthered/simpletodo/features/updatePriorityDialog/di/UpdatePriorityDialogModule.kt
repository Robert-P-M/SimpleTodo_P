package at.robthered.simpletodo.features.updatePriorityDialog.di

import androidx.lifecycle.SavedStateHandle
import at.robthered.simpletodo.features.dataSource.domain.repository.priority.PriorityRepository
import at.robthered.simpletodo.features.updatePriorityDialog.presentation.UpdatePriorityDialogViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val updatePriorityDialogModule = module {

    viewModel {
        UpdatePriorityDialogViewModel(
            savedStateHandle = get<SavedStateHandle>(),
            priorityRepository = get<PriorityRepository>()
        )
    }
    viewModelOf(::UpdatePriorityDialogViewModel)
}