package at.robthered.simpletodo.features.dataSource.di

import androidx.room.Room
import at.robthered.simpletodo.features.alarm.domain.ScheduleAlarmUseCase
import at.robthered.simpletodo.features.core.domain.eventBus.events.DueEventBus
import at.robthered.simpletodo.features.dataSource.data.repository.archived.LocalArchivedRepositoryImpl
import at.robthered.simpletodo.features.dataSource.data.repository.completed.LocalCompletedRepositoryImpl
import at.robthered.simpletodo.features.dataSource.data.repository.priority.LocalPriorityRepositoryImpl
import at.robthered.simpletodo.features.dataSource.data.repository.section.LocalSectionRepositoryImpl
import at.robthered.simpletodo.features.dataSource.data.repository.task.LocalTaskRepositoryImpl
import at.robthered.simpletodo.features.dataSource.data.local.database.AppDatabase
import at.robthered.simpletodo.features.dataSource.data.local.dao.archived.ArchivedDao
import at.robthered.simpletodo.features.dataSource.data.local.dao.completed.CompletedDao
import at.robthered.simpletodo.features.dataSource.data.local.dao.event.EventDao
import at.robthered.simpletodo.features.dataSource.data.local.dao.eventToTask.EventToTaskDao
import at.robthered.simpletodo.features.dataSource.data.local.dao.link.LinkDao
import at.robthered.simpletodo.features.dataSource.data.local.dao.priority.PriorityDao
import at.robthered.simpletodo.features.dataSource.data.local.dao.section.SectionDao
import at.robthered.simpletodo.features.dataSource.data.local.dao.sectionWithTasks.SectionWithTasksWithDetailsDao
import at.robthered.simpletodo.features.dataSource.data.local.dao.task.TaskDao
import at.robthered.simpletodo.features.dataSource.data.local.helper.TransactionProviderImpl
import at.robthered.simpletodo.features.dataSource.data.local.dao.taskWithDetails.TaskWithDetailsDao
import at.robthered.simpletodo.features.dataSource.data.local.migrations.Migration_1_2
import at.robthered.simpletodo.features.dataSource.data.local.migrations.Migration_2_3
import at.robthered.simpletodo.features.dataSource.data.repository.archived.ArchivedRepositoryImpl
import at.robthered.simpletodo.features.dataSource.data.repository.completed.CompletedRepositoryImpl
import at.robthered.simpletodo.features.dataSource.data.repository.event.EventRepositoryImpl
import at.robthered.simpletodo.features.dataSource.data.repository.event.LocalEventRepositoryImpl
import at.robthered.simpletodo.features.dataSource.data.repository.eventToTask.EventToTaskRepositoryImpl
import at.robthered.simpletodo.features.dataSource.data.repository.eventToTask.LocalEventToTaskRepositoryImpl
import at.robthered.simpletodo.features.dataSource.data.repository.link.LinkRepositoryImpl
import at.robthered.simpletodo.features.dataSource.data.repository.link.LocalLinkRepositoryImpl
import at.robthered.simpletodo.features.dataSource.data.repository.priority.PriorityRepositoryImpl
import at.robthered.simpletodo.features.dataSource.data.repository.section.SectionRepositoryImpl
import at.robthered.simpletodo.features.dataSource.data.repository.task.TaskRepositoryImpl
import at.robthered.simpletodo.features.dataSource.data.repository.taskWithDetails.LocalTaskWithDetailsRepositoryImpl
import at.robthered.simpletodo.features.dataSource.data.repository.taskWithDetails.TaskWithDetailsRepositoryImpl
import at.robthered.simpletodo.features.dataSource.data.repository.sectionWithTasks.LocalSectionWithTasksWithDetailRepositoryImpl
import at.robthered.simpletodo.features.dataSource.data.repository.sectionWithTasks.SectionWithTasksWithDetailRepositoryImpl
import at.robthered.simpletodo.features.dataSource.data.use_case.archived.ArchiveTaskUseCaseImpl
import at.robthered.simpletodo.features.dataSource.data.use_case.completed.CompleteTaskUseCaseImpl
import at.robthered.simpletodo.features.dataSource.data.use_case.task.LoadTaskUseCaseImpl
import at.robthered.simpletodo.features.dataSource.data.use_case.event.UpdateTaskEventUseCaseImpl
import at.robthered.simpletodo.features.dataSource.data.use_case.link.RemoveLinkUseCaseImpl
import at.robthered.simpletodo.features.dataSource.data.use_case.link.SaveLinkToTaskUseCaseImpl
import at.robthered.simpletodo.features.dataSource.data.use_case.section.DeleteSectionUseCaseImpl
import at.robthered.simpletodo.features.dataSource.data.use_case.section.SaveSectionUseCaseImpl
import at.robthered.simpletodo.features.dataSource.data.use_case.task.DeleteTaskUseCaseImpl
import at.robthered.simpletodo.features.dataSource.data.use_case.taskWithDetails.SaveTaskWithDetailsUseCaseImpl
import at.robthered.simpletodo.features.dataSource.domain.local.helper.TransactionProvider
import at.robthered.simpletodo.features.dataSource.domain.repository.archived.LocalArchivedRepository
import at.robthered.simpletodo.features.dataSource.domain.repository.completed.LocalCompletedRepository
import at.robthered.simpletodo.features.dataSource.domain.repository.priority.LocalPriorityRepository
import at.robthered.simpletodo.features.dataSource.domain.repository.section.LocalSectionRepository
import at.robthered.simpletodo.features.dataSource.domain.repository.task.LocalTaskRepository
import at.robthered.simpletodo.features.dataSource.domain.repository.archived.ArchivedRepository
import at.robthered.simpletodo.features.dataSource.domain.repository.completed.CompletedRepository
import at.robthered.simpletodo.features.dataSource.domain.repository.event.EventRepository
import at.robthered.simpletodo.features.dataSource.domain.repository.event.LocalEventRepository
import at.robthered.simpletodo.features.dataSource.domain.repository.eventToTask.EventToTaskRepository
import at.robthered.simpletodo.features.dataSource.domain.repository.eventToTask.LocalEventToTaskRepository
import at.robthered.simpletodo.features.dataSource.domain.repository.link.LinkRepository
import at.robthered.simpletodo.features.dataSource.domain.repository.link.LocalLinkRepository
import at.robthered.simpletodo.features.dataSource.domain.repository.priority.PriorityRepository
import at.robthered.simpletodo.features.dataSource.domain.repository.section.SectionRepository
import at.robthered.simpletodo.features.dataSource.domain.repository.task.TaskRepository
import at.robthered.simpletodo.features.dataSource.domain.repository.taskWithDetails.LocalTaskWithDetailsRepository
import at.robthered.simpletodo.features.dataSource.domain.repository.taskWithDetails.TaskWithDetailsRepository
import at.robthered.simpletodo.features.dataSource.domain.repository.sectionWithTasks.LocalSectionWithTasksWithDetailRepository
import at.robthered.simpletodo.features.dataSource.domain.repository.sectionWithTasks.SectionWithTasksWithDetailsRepository
import at.robthered.simpletodo.features.dataSource.domain.use_case.archived.ArchiveTaskUseCase
import at.robthered.simpletodo.features.dataSource.domain.use_case.completed.CompleteTaskUseCase
import at.robthered.simpletodo.features.dataSource.domain.use_case.task.LoadTaskUseCase
import at.robthered.simpletodo.features.dataSource.domain.use_case.event.UpdateTaskEventUseCase
import at.robthered.simpletodo.features.dataSource.domain.use_case.link.RemoveLinkUseCase
import at.robthered.simpletodo.features.dataSource.domain.use_case.link.SaveLinkToTaskUseCase
import at.robthered.simpletodo.features.dataSource.domain.use_case.section.DeleteSectionUseCase
import at.robthered.simpletodo.features.dataSource.domain.use_case.section.SaveSectionUseCase
import at.robthered.simpletodo.features.dataSource.domain.use_case.task.DeleteTaskUseCase
import at.robthered.simpletodo.features.dataSource.domain.use_case.taskWithDetails.SaveTaskWithDetailsUseCase
import at.robthered.simpletodo.features.shared.image.domain.use_case.TransformSharedUrlToLinkModelUseCase
import at.robthered.simpletodo.features.dataSource.domain.use_case.UpdateTaskWithDetailsUseCase
import at.robthered.simpletodo.features.dataSource.data.use_case.UpdateTaskWithDetailsUseCaseImpl
import at.robthered.simpletodo.features.dataSource.domain.use_case.priority.CreatePriorityUseCase
import at.robthered.simpletodo.features.dataSource.data.use_case.priority.CreatePriorityUseCaseImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataSourceModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        )
            .addMigrations(
                Migration_1_2,
                Migration_2_3
            )
            .build()
    }

    single<TransactionProvider> {
        TransactionProviderImpl(
            database = get<AppDatabase>()
        )
    }

    single<TaskDao> {
        get<AppDatabase>().taskDao()
    }
    single<CompletedDao> {
        get<AppDatabase>().completedDao()
    }
    single<ArchivedDao> {
        get<AppDatabase>().archivedDao()
    }
    single<SectionDao> {
        get<AppDatabase>().sectionDao()
    }
    single<PriorityDao> {
        get<AppDatabase>().priorityDao()
    }
    single<EventDao> {
        get<AppDatabase>().eventDao()
    }
    single<TaskWithDetailsDao> {
        get<AppDatabase>().taskWithDetailsDao()
    }
    single<SectionWithTasksWithDetailsDao> {
        get<AppDatabase>().sectionWithTaskWithDetailsDao()
    }

    single<EventToTaskDao> {
        get<AppDatabase>().eventToTaskDao()
    }

    single<LinkDao> {
        get<AppDatabase>().linkDao()
    }

    single<LocalArchivedRepository> {
        LocalArchivedRepositoryImpl(
            archivedDao = get<ArchivedDao>()
        )
    }

    single<ArchivedRepository> {
        ArchivedRepositoryImpl(
            localArchivedRepository = get<LocalArchivedRepository>()
        )
    }

    single<LocalCompletedRepository> {
        LocalCompletedRepositoryImpl(
            completedDao = get<CompletedDao>()
        )
    }

    single<CompletedRepository> {
        CompletedRepositoryImpl(
            localCompletedRepository = get<LocalCompletedRepository>()
        )
    }

    single<LocalPriorityRepository> {
        LocalPriorityRepositoryImpl(
            priorityDao = get<PriorityDao>()
        )
    }

    single<PriorityRepository> {
        PriorityRepositoryImpl(
            localPriorityRepository = get<LocalPriorityRepository>()
        )
    }

    single<LocalSectionRepository> {
        LocalSectionRepositoryImpl(
            sectionDao = get<SectionDao>()
        )
    }

    single<SectionRepository> {
        SectionRepositoryImpl(
            localSectionRepository = get<LocalSectionRepository>()
        )
    }

    single<LocalTaskRepository> {
        LocalTaskRepositoryImpl(
            taskDao = get<TaskDao>()
        )
    }

    single<TaskRepository> {
        TaskRepositoryImpl(
            localTaskRepository = get<LocalTaskRepository>()
        )
    }


    single<LocalEventToTaskRepository> {
        LocalEventToTaskRepositoryImpl(
            eventToTaskDao = get<EventToTaskDao>()
        )
    }

    single<EventToTaskRepository> {
        EventToTaskRepositoryImpl(
            localEventToTaskRepository = get<LocalEventToTaskRepository>()
        )
    }

    single<LocalEventRepository> {
        LocalEventRepositoryImpl(
            eventDao = get<EventDao>()
        )
    }

    single<EventRepository> {
        EventRepositoryImpl(
            localEventRepository = get<LocalEventRepository>()
        )
    }

    single<LocalLinkRepository> {
        LocalLinkRepositoryImpl(
            linkDao = get<LinkDao>()
        )
    }

    single<LinkRepository> {
        LinkRepositoryImpl(
            localLinkRepository = get<LocalLinkRepository>()
        )
    }

    single<LocalTaskWithDetailsRepository> {
        LocalTaskWithDetailsRepositoryImpl(
            taskDao = get<TaskDao>(),
            priorityDao = get<PriorityDao>(),
            eventDao = get<EventDao>(),
            transactionProvider = get<TransactionProvider>(),
            taskWithDetailsDao = get<TaskWithDetailsDao>(),
            linkDao = get<LinkDao>()
        )
    }
    single<TaskWithDetailsRepository> {
        TaskWithDetailsRepositoryImpl(
            localTaskWithDetailsRepository = get()
        )
    }

    single<LocalSectionWithTasksWithDetailRepository> {
        LocalSectionWithTasksWithDetailRepositoryImpl(
            sectionWithTasksWithDetailsDao = get<SectionWithTasksWithDetailsDao>(),
            taskWithDetailsDao = get<TaskWithDetailsDao>()
        )
    }
    single<SectionWithTasksWithDetailsRepository> {
        SectionWithTasksWithDetailRepositoryImpl(
            localSectionWithTasksWithDetailsRepository = get<LocalSectionWithTasksWithDetailRepository>()
        )
    }

    single<LoadTaskUseCase> {
        LoadTaskUseCaseImpl(
            taskWithDetailsRepository = get<TaskWithDetailsRepository>()
        )
    }

    single<DeleteTaskUseCase> {
        DeleteTaskUseCaseImpl(
            taskRepository = get<TaskRepository>()
        )
    }

    single<DeleteSectionUseCase> {
        DeleteSectionUseCaseImpl(
            sectionRepository = get<SectionRepository>()
        )
    }

    single<CompleteTaskUseCase> {
        CompleteTaskUseCaseImpl(
            completedRepository = get<CompletedRepository>()
        )
    }

    single<ArchiveTaskUseCase> {
        ArchiveTaskUseCaseImpl(
            archivedRepository = get<ArchivedRepository>()
        )
    }


    single<UpdateTaskEventUseCase> {
        UpdateTaskEventUseCaseImpl(
            taskWithDetailsRepository = get<TaskWithDetailsRepository>(),
            dueEventBus = get<DueEventBus>(),
        )
    }

    single<SaveSectionUseCase> {
        SaveSectionUseCaseImpl(
            sectionRepository = get<SectionRepository>()
        )
    }

    single<SaveTaskWithDetailsUseCase> {
        SaveTaskWithDetailsUseCaseImpl(
            taskWithDetailsRepository = get<TaskWithDetailsRepository>(),
            scheduleAlarmUseCase = get<ScheduleAlarmUseCase>(),
        )
    }

    single<UpdateTaskWithDetailsUseCase> {
        UpdateTaskWithDetailsUseCaseImpl(
            taskWithDetailsRepository = get<TaskWithDetailsRepository>()
        )
    }

    single<SaveLinkToTaskUseCase> {
        SaveLinkToTaskUseCaseImpl(
            linkRepository = get<LinkRepository>(),
            transformSharedUrlToLinkModelUseCase = get<TransformSharedUrlToLinkModelUseCase>(),
        )
    }

    single<CreatePriorityUseCase> {
        CreatePriorityUseCaseImpl(
            priorityRepository = get<PriorityRepository>()
        )
    }

    single<RemoveLinkUseCase> {
        RemoveLinkUseCaseImpl(
            linkRepository = get()
        )
    }
    
    
}