package at.robthered.simpletodo.features.taskDetailsDialog.di

import androidx.lifecycle.SavedStateHandle
import at.robthered.simpletodo.features.core.domain.eventBus.events.DueEventBus
import at.robthered.simpletodo.features.core.domain.eventBus.events.PriorityModelEventBus
import at.robthered.simpletodo.features.core.domain.eventBus.events.SharedUrlModelEventBus
import at.robthered.simpletodo.features.core.domain.state.expandedItemsState.ExpandedItemsStateManager
import at.robthered.simpletodo.features.dataSource.domain.use_case.event.UpdateTaskEventUseCase
import at.robthered.simpletodo.features.dataSource.domain.use_case.link.RemoveLinkUseCase
import at.robthered.simpletodo.features.dataSource.domain.use_case.link.SaveLinkToTaskUseCase
import at.robthered.simpletodo.features.dataSource.domain.use_case.task.DeleteTaskUseCase
import at.robthered.simpletodo.features.dataSource.domain.use_case.task.LoadTaskUseCase
import at.robthered.simpletodo.features.core.domain.state_manager.completed.CompletedStateManager
import at.robthered.simpletodo.features.dataSource.domain.use_case.priority.CreatePriorityUseCase
import at.robthered.simpletodo.features.taskDetailsDialog.presentation.TaskDetailsDialogViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val taskDetailsDialogModule = module {
    single { CoroutineScope(SupervisorJob() + Dispatchers.Default) }

    viewModel {
        TaskDetailsDialogViewModel(
            savedStateHandle = get<SavedStateHandle>(),
            loadTaskUseCase = get<LoadTaskUseCase>(),
            completedStateManager = get<CompletedStateManager>(),
            dueEventBus = get<DueEventBus>(),
            updateTaskEventUseCase = get<UpdateTaskEventUseCase>(),
            deleteTaskUseCase = get<DeleteTaskUseCase>(),
            shareUrlModelEventBus = get<SharedUrlModelEventBus>(),
            saveLinkToTaskUseCase = get<SaveLinkToTaskUseCase>(),
            removeLinkUseCase = get<RemoveLinkUseCase>(),
            priorityModelEventBus = get<PriorityModelEventBus>(),
            createPriorityUseCase = get<CreatePriorityUseCase>(),
        )
    }
}