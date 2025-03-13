package at.robthered.simpletodo.features.homeScreen.di

import at.robthered.simpletodo.features.core.domain.state.expandedItemsState.ExpandedItemsStateManager
import at.robthered.simpletodo.features.core.domain.state_manager.archived.ArchivedStateManager
import at.robthered.simpletodo.features.core.domain.state_manager.completed.CompletedStateManager
import at.robthered.simpletodo.features.dataSource.domain.repository.sectionWithTasks.SectionWithTasksWithDetailsRepository
import at.robthered.simpletodo.features.dataSource.domain.repository.taskWithDetails.TaskWithDetailsRepository
import at.robthered.simpletodo.features.dataSource.domain.use_case.section.DeleteSectionUseCase
import at.robthered.simpletodo.features.dataSource.domain.use_case.task.DeleteTaskUseCase
import at.robthered.simpletodo.features.homeScreen.data.use_case.LoadHomeItemsUseCaseImpl
import at.robthered.simpletodo.features.homeScreen.domain.use_case.LoadHomeItemsUseCase
import at.robthered.simpletodo.features.homeScreen.presentation.HomeViewModel
import at.robthered.simpletodo.features.shared.locale.domain.state.AppLocaleStateManager
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {

    single<LoadHomeItemsUseCase> {
        LoadHomeItemsUseCaseImpl(
            taskWithDetailsRepository = get<TaskWithDetailsRepository>(),
            sectionWithTasksWithDetailsRepository = get<SectionWithTasksWithDetailsRepository>()
        )
    }

    viewModel {
        HomeViewModel(
            completedStateManager = get<CompletedStateManager>(),
            archivedStateManager = get<ArchivedStateManager>(),
            expandedTasksStateManager = get<ExpandedItemsStateManager>(),
            expandedSectionsStateManager = get<ExpandedItemsStateManager>(),
            appLocaleStateManager = get<AppLocaleStateManager>(),
            deleteTaskUseCase = get<DeleteTaskUseCase>(),
            deleteSectionUseCase = get<DeleteSectionUseCase>(),
            loadHomeItemsUseCase = get<LoadHomeItemsUseCase>(),
        )
    }



}