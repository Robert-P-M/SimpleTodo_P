package at.robthered.simpletodo.features.priorityDialogScreen.di

import at.robthered.simpletodo.features.core.domain.eventBus.events.PriorityModelEventBus
import at.robthered.simpletodo.features.core.domain.state_manager.priority.PriorityStateManager
import at.robthered.simpletodo.features.priorityDialogScreen.presentation.PriorityPickerDialogViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val priorityPickerDialogModule = module {
    viewModel {
        PriorityPickerDialogViewModel(
            priorityModelEventBus = get<PriorityModelEventBus>(),
            priorityStateManager = get<PriorityStateManager>()
        )
    }
}