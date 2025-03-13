package at.robthered.simpletodo.features.core.di

import android.content.Context
import android.content.SharedPreferences
import at.robthered.simpletodo.features.core.data.eventBus.EventBusImpl
import at.robthered.simpletodo.features.core.data.state_manager.archived.ArchivedStateManagerImpl
import at.robthered.simpletodo.features.core.data.state_manager.completed.CompletedStateManagerImpl
import at.robthered.simpletodo.features.core.data.state_manager.dueEvent.DueEventStateManagerImpl
import at.robthered.simpletodo.features.core.data.state_manager.event.EventStateManagerImpl
import at.robthered.simpletodo.features.core.data.state_manager.priority.PriorityStateManagerImpl
import at.robthered.simpletodo.features.core.data.state_manager.section.SectionStateManagerImpl
import at.robthered.simpletodo.features.core.data.state_manager.sharedUrls.SharedUrlsStateManagerImpl
import at.robthered.simpletodo.features.core.data.state_manager.task.TaskStateManagerImpl
import at.robthered.simpletodo.features.core.data.use_case.validation.ValidateSectionTitleUseCaseImpl
import at.robthered.simpletodo.features.core.data.use_case.validation.ValidateTaskDescriptionUseCaseImpl
import at.robthered.simpletodo.features.core.data.use_case.validation.ValidateTaskTitleUseCaseImpl
import at.robthered.simpletodo.features.core.domain.eventBus.events.DueEventBus
import at.robthered.simpletodo.features.core.domain.eventBus.events.PriorityModelEventBus
import at.robthered.simpletodo.features.core.domain.eventBus.events.SharedUrlModelEventBus
import at.robthered.simpletodo.features.core.domain.state.expandedItemsState.ExpandedItemsStateManager
import at.robthered.simpletodo.features.core.domain.state.scaffoldState.MainScaffoldStateManager
import at.robthered.simpletodo.features.core.domain.state_manager.archived.ArchivedStateManager
import at.robthered.simpletodo.features.core.domain.state_manager.completed.CompletedStateManager
import at.robthered.simpletodo.features.core.domain.state_manager.dueEvent.DueEventStateManager
import at.robthered.simpletodo.features.core.domain.state_manager.event.EventStateManager
import at.robthered.simpletodo.features.core.domain.state_manager.priority.PriorityStateManager
import at.robthered.simpletodo.features.core.domain.state_manager.section.SectionStateManager
import at.robthered.simpletodo.features.core.domain.state_manager.sharedUrls.SharedUrlsStateManager
import at.robthered.simpletodo.features.core.domain.state_manager.task.TaskStateManager
import at.robthered.simpletodo.features.core.domain.use_case.validation.ValidateSectionTitleUseCase
import at.robthered.simpletodo.features.core.domain.use_case.validation.ValidateTaskDescriptionUseCase
import at.robthered.simpletodo.features.core.domain.use_case.validation.ValidateTaskTitleUseCase
import at.robthered.simpletodo.features.core.presentation.state.ExpandedItemsStateManagerImpl
import at.robthered.simpletodo.features.core.presentation.state.scaffoldState.AppScaffoldStateManagerImpl
import at.robthered.simpletodo.features.dataSource.domain.use_case.archived.ArchiveTaskUseCase
import at.robthered.simpletodo.features.dataSource.domain.use_case.completed.CompleteTaskUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreModule = module {


    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            "simple_todo_prefs",
            Context.MODE_PRIVATE
        )
    }
    single<CoroutineScope> { CoroutineScope(SupervisorJob() + Dispatchers.Default) }

    factory<ExpandedItemsStateManager> {
        ExpandedItemsStateManagerImpl(
            coroutineScope = get<CoroutineScope>()
        )
    }

    single<ValidateTaskTitleUseCase> { ValidateTaskTitleUseCaseImpl() }
    single<ValidateTaskDescriptionUseCase> { ValidateTaskDescriptionUseCaseImpl() }
    single<ValidateSectionTitleUseCase> { ValidateSectionTitleUseCaseImpl() }

    singleOf(::AppScaffoldStateManagerImpl) bind MainScaffoldStateManager::class

    single<DueEventBus> {
        EventBusImpl()
    }
    single<SharedUrlModelEventBus> {
        EventBusImpl()
    }
    single<PriorityModelEventBus> {
        EventBusImpl()
    }

    factory <TaskStateManager> {
        TaskStateManagerImpl(
            validateTaskTitleUseCase = get<ValidateTaskTitleUseCase>(),
            validateTaskDescriptionUseCase = get<ValidateTaskDescriptionUseCase>(),
        )
    }
    factory <PriorityStateManager> {
        PriorityStateManagerImpl()
    }

    factory <DueEventStateManager> {
        DueEventStateManagerImpl()
    }

    factory <SharedUrlsStateManager> {
        SharedUrlsStateManagerImpl(
            coroutineScope = get<CoroutineScope>(),
            shareUrlModelEventBus = get<SharedUrlModelEventBus>()
        )
    }
    factory <SectionStateManager> {
        SectionStateManagerImpl(
            validateSectionTitleUseCase = get<ValidateSectionTitleUseCase>(),
        )
    }

    factory<EventStateManager> {
        EventStateManagerImpl(
            dueEventBus = get<DueEventBus>(),
            coroutineScope = get<CoroutineScope>()
        )
    }
    factory<ArchivedStateManager> {
        ArchivedStateManagerImpl(
            coroutineScope = get<CoroutineScope>(),
            archiveTaskUseCase = get<ArchiveTaskUseCase>()
        )
    }
    factory<CompletedStateManager> {
        CompletedStateManagerImpl(
            coroutineScope = get<CoroutineScope>(),
            completeTaskUseCase = get<CompleteTaskUseCase>()
        )
    }

}