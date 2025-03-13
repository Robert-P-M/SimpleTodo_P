package at.robthered.simpletodo.features.addTaskDialog.di

import androidx.lifecycle.SavedStateHandle
import at.robthered.simpletodo.features.addTaskDialog.presentation.AddTaskDialogViewModel
import at.robthered.simpletodo.features.core.domain.eventBus.events.PriorityModelEventBus
import at.robthered.simpletodo.features.core.domain.state_manager.event.EventStateManager
import at.robthered.simpletodo.features.core.domain.state_manager.priority.PriorityStateManager
import at.robthered.simpletodo.features.core.domain.state_manager.sharedUrls.SharedUrlsStateManager
import at.robthered.simpletodo.features.core.domain.state_manager.task.TaskStateManager
import at.robthered.simpletodo.features.dataSource.domain.use_case.taskWithDetails.SaveTaskWithDetailsUseCase
import at.robthered.simpletodo.features.shared.image.domain.use_case.TransformSharedUrlToLinkModelUseCase
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val addTaskDialogModule = module {

    viewModel {
        AddTaskDialogViewModel(
            savedStateHandle = get<SavedStateHandle>(),
            taskStateManager = get<TaskStateManager>(),
            priorityStateManager = get<PriorityStateManager>(),
            sharedUrlsStateManager = get<SharedUrlsStateManager>(),
            eventStateManager = get<EventStateManager>(),
            saveTaskWithDetailsUseCase = get<SaveTaskWithDetailsUseCase>(),
            transformSharedUrlToLinkModelUseCase = get<TransformSharedUrlToLinkModelUseCase>(),
            priorityModelEventBus = get<PriorityModelEventBus>()
        )
    }
}