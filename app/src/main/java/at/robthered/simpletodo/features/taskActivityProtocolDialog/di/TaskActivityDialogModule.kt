package at.robthered.simpletodo.features.taskActivityProtocolDialog.di

import androidx.lifecycle.SavedStateHandle
import at.robthered.simpletodo.features.dataSource.domain.repository.archived.ArchivedRepository
import at.robthered.simpletodo.features.dataSource.domain.repository.completed.CompletedRepository
import at.robthered.simpletodo.features.dataSource.domain.repository.event.EventRepository
import at.robthered.simpletodo.features.dataSource.domain.repository.link.LinkRepository
import at.robthered.simpletodo.features.dataSource.domain.repository.priority.PriorityRepository
import at.robthered.simpletodo.features.dataSource.domain.repository.task.TaskRepository
import at.robthered.simpletodo.features.taskActivityProtocolDialog.data.use_case.TaskActivityUseCaseImpl
import at.robthered.simpletodo.features.taskActivityProtocolDialog.domain.use_case.TaskActivityUseCase
import at.robthered.simpletodo.features.taskActivityProtocolDialog.presentation.TaskActivityDialogViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val taskActivityDialogModule = module {
    single<TaskActivityUseCase> {
        TaskActivityUseCaseImpl(
            archivedRepository = get<ArchivedRepository>(),
            completedRepository = get<CompletedRepository>(),
            eventRepository = get<EventRepository>(),
            priorityRepository = get<PriorityRepository>(),
            taskRepository = get<TaskRepository>(),
            taskListRepository = get<TaskRepository>(),
            linkRepository = get<LinkRepository>()
        )
    }
    viewModel {
        TaskActivityDialogViewModel(
            taskActivityUseCase = get<TaskActivityUseCase>(),
            savedStateHandle = get<SavedStateHandle>()
        )
    }
}