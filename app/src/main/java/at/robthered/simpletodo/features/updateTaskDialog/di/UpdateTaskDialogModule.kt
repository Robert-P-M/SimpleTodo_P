package at.robthered.simpletodo.features.updateTaskDialog.di

import at.robthered.simpletodo.features.core.domain.eventBus.events.DueEventBus
import at.robthered.simpletodo.features.core.domain.eventBus.events.PriorityModelEventBus
import at.robthered.simpletodo.features.core.domain.eventBus.events.SharedUrlModelEventBus
import at.robthered.simpletodo.features.core.domain.state_manager.event.EventStateManager
import at.robthered.simpletodo.features.core.domain.state_manager.priority.PriorityStateManager
import at.robthered.simpletodo.features.core.domain.state_manager.task.TaskStateManager
import at.robthered.simpletodo.features.dataSource.domain.use_case.UpdateTaskWithDetailsUseCase
import at.robthered.simpletodo.features.dataSource.domain.use_case.link.RemoveLinkUseCase
import at.robthered.simpletodo.features.dataSource.domain.use_case.task.LoadTaskUseCase
import at.robthered.simpletodo.features.shared.image.domain.use_case.TransformSharedUrlToLinkModelUseCase
import at.robthered.simpletodo.features.updateTaskDialog.presentation.UpdateTaskDialogViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val updateTaskDialogModule = module {

    viewModel {
        UpdateTaskDialogViewModel(
            savedStateHandle = get(),
            taskStateManager = get<TaskStateManager>(),
            priorityStateManager = get<PriorityStateManager>(),
            eventStateManager = get<EventStateManager>(),
            dueEventBus = get<DueEventBus>(),
            loadTaskUseCase = get<LoadTaskUseCase>(),
            removeLinkUseCase = get<RemoveLinkUseCase>(),
            shareUrlModelEventBus = get<SharedUrlModelEventBus>(),
            priorityModelEventBus = get<PriorityModelEventBus>(),
            transformSharedUrlToLinkModelUseCase = get<TransformSharedUrlToLinkModelUseCase>(),
            updateTaskWithDetailsUseCase = get<UpdateTaskWithDetailsUseCase>()
        )
    }
}