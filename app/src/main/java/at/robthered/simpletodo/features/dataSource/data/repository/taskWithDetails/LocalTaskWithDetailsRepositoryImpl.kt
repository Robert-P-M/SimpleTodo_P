package at.robthered.simpletodo.features.dataSource.data.repository.taskWithDetails

import android.util.Log
import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.core.utils.safeCall
import at.robthered.simpletodo.features.dataSource.data.local.dao.event.EventDao
import at.robthered.simpletodo.features.dataSource.data.local.dao.link.LinkDao
import at.robthered.simpletodo.features.dataSource.data.local.dao.priority.PriorityDao
import at.robthered.simpletodo.features.dataSource.data.local.dao.task.TaskDao
import at.robthered.simpletodo.features.dataSource.data.local.dao.taskWithDetails.TaskWithDetailsDao
import at.robthered.simpletodo.features.dataSource.data.local.mapper.toArchivedModel
import at.robthered.simpletodo.features.dataSource.data.local.mapper.toCompletedModel
import at.robthered.simpletodo.features.dataSource.data.local.mapper.toEventEntity
import at.robthered.simpletodo.features.dataSource.data.local.mapper.toEventModel
import at.robthered.simpletodo.features.dataSource.data.local.mapper.toLinkEntity
import at.robthered.simpletodo.features.dataSource.data.local.mapper.toLinkModel
import at.robthered.simpletodo.features.dataSource.data.local.mapper.toModel
import at.robthered.simpletodo.features.dataSource.data.local.mapper.toPriorityEntity
import at.robthered.simpletodo.features.dataSource.data.local.mapper.toPriorityModel
import at.robthered.simpletodo.features.dataSource.data.local.mapper.toTaskEntity
import at.robthered.simpletodo.features.dataSource.data.local.mapper.toTaskModel
import at.robthered.simpletodo.features.dataSource.data.repository.utils.buildNestedTasksWithDetailsFlow
import at.robthered.simpletodo.features.dataSource.domain.local.helper.TransactionProvider
import at.robthered.simpletodo.features.dataSource.domain.local.model.EventModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.TaskModel
import at.robthered.simpletodo.features.dataSource.domain.local.relation.TaskWithDetailsAndChildrenModel
import at.robthered.simpletodo.features.dataSource.domain.local.relation.TaskWithDetailsModel
import at.robthered.simpletodo.features.dataSource.domain.repository.taskWithDetails.LocalTaskWithDetailsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

data class InsertTaskWithDetailsResult(
    val event: EventModel?,
)

class LocalTaskWithDetailsRepositoryImpl(
    private val taskDao: TaskDao,
    private val priorityDao: PriorityDao,
    private val eventDao: EventDao,
    private val linkDao: LinkDao,
    private val transactionProvider: TransactionProvider,
    private val taskWithDetailsDao: TaskWithDetailsDao
) : LocalTaskWithDetailsRepository {
    override fun get(): Flow<List<TaskWithDetailsModel>> {
        return taskWithDetailsDao.get().map { entities -> entities.map { it.toModel() } }
    }

    override fun get(taskId: Long?): Flow<TaskWithDetailsModel?> {
        return taskWithDetailsDao.get(taskId).map { it?.toModel() }
    }

    override fun getTasksOfParentWithDetails(parentTaskId: Long?): Flow<List<TaskWithDetailsModel>> {
        return taskWithDetailsDao.getTasksOfParentWithDetails(parentTaskId)
            .map { entities ->
                entities.map {

                    it.toModel()
                }
            }
    }


    override suspend fun insert(taskWithDetailsModel: TaskWithDetailsModel): Result<InsertTaskWithDetailsResult, Error> {
        return safeCall {
            transactionProvider.runAsTransaction {
                val taskEntity = taskWithDetailsModel.task.toTaskEntity()
                val taskId = taskDao.insert(taskEntity = taskEntity)
                taskWithDetailsModel.priority.forEach { priorityModel: PriorityModel ->
                    priorityDao.insertPriority(priorityEntity = priorityModel.toPriorityEntity().copy(taskId = taskId))
                }
                taskWithDetailsModel.links.forEach { link ->
                    linkDao.insert(linkEntity = link.toLinkEntity().copy(taskId = taskId))
                }
                val eventId: Long? = taskWithDetailsModel.event?.let { event ->
                    eventDao.insert(
                        entity = event.copy(
                            taskId = taskId
                        )
                            .toEventEntity()
                    )

                }
                Log.d("DEBUG", "LocalTaskWithDetailsRepositoryImpl - eventId: $eventId")
                taskDao.upsertTask(
                    taskEntity = taskEntity.copy(
                        taskId = taskId,
                        currentEventId = eventId
                    )
                )
                val event = taskWithDetailsModel.event?.copy(eventId = eventId)
                Log.d("DEBUG", "LocalTaskWithDetailsRepositoryImpl - event: $event")
                InsertTaskWithDetailsResult(
                    event = event
                )

            }
        }
    }



    override suspend fun upsert(taskWithDetailsModel: TaskWithDetailsModel): Result<Unit, Error> {
        return safeCall {
            transactionProvider.runAsTransaction {
                val taskEntity = taskWithDetailsModel.task.toTaskEntity()
               taskDao.upsertTask(taskEntity = taskEntity)
                taskWithDetailsModel
                    .priority
                    .forEach { priorityModel: PriorityModel ->
                    priorityDao
                        .insertPriority(
                            priorityEntity = priorityModel.copy(taskId = taskEntity.taskId).toPriorityEntity()
                        )
                }
                taskWithDetailsModel
                    .links
                    .forEach { link ->
                    linkDao
                        .insert(
                            linkEntity = link.toLinkEntity().copy(taskId = taskEntity.taskId)
                        )
                }
                val eventId = taskWithDetailsModel.event?.let { event ->
                    eventDao.insert(
                        entity = event
                            .copy(taskId = taskEntity.taskId)
                            .toEventEntity()

                    )
                }
                taskDao.upsertTask(
                    taskEntity = taskEntity.copy(
                        taskId = taskEntity.taskId,
                        currentEventId = eventId,
                        updatedAt = Clock.System.now().toEpochMilliseconds()
                    )
                )
            }
        }

    }

    override suspend fun updateTaskEvent(newEventModel: EventModel, taskId: Long?): Result<Unit, Error> {
        return safeCall {
            transactionProvider.runAsTransaction {
                val eventId = eventDao.upsert(entity = newEventModel.toEventEntity())
                val taskEntity = taskDao.getTask(taskId)
                    .firstOrNull()
                taskEntity?.let {task ->
                    taskDao.upsertTask(task.copy(currentEventId = if(newEventModel.isActive) eventId else null))
                }

            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getTaskWithDetails(taskId: Long?, depth: Int): Flow<TaskWithDetailsAndChildrenModel?> {
        return taskWithDetailsDao.get(taskId)
            .flatMapLatest { task ->
                task?.let { it ->
                    buildNestedTasksWithDetailsFlow(
                        taskId = it.task.taskId,
                        depth = depth,
                        getTasksOfParentWithDetails = { parentId ->
                            taskWithDetailsDao.getTasksOfParentWithDetails(parentTaskId = parentId).map { entities -> entities.map { it.toModel() } }
                        }
                    ).map { children ->
                        TaskWithDetailsAndChildrenModel(
                            task = it.task.toTaskModel(),
                            completed = it.completed.map { entity -> entity.toCompletedModel() },
                            archived = it.archived.map { entity -> entity.toArchivedModel() },
                            priority = it.priority.map { entity -> entity.toPriorityModel() },
                            event = it.event?.toEventModel(),
                            links = it.links.map { entity -> entity.toLinkModel() },
                            tasks = children
                        )
                    }
                } ?: flowOf(null)
            }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAllTasksWithDetails(depth: Int): Flow<List<TaskWithDetailsAndChildrenModel>> {
        return taskWithDetailsDao.get()
            .flatMapLatest { tasks ->
                flow {
                    val tasksWithChildren = tasks.map { task ->
                        val childrenFlow = buildNestedTasksWithDetailsFlow(
                            taskId = task.task.taskId,
                            depth = depth - 1,
                            getTasksOfParentWithDetails = { parentId ->
                                taskWithDetailsDao.getTasksOfParentWithDetails(parentTaskId = parentId).map { entities -> entities.map { it.toModel() } }
                            }
                        )
                        val children = childrenFlow.first()
                        TaskWithDetailsAndChildrenModel(
                            task = task.task.toTaskModel(),
                            completed = task.completed.map { entity -> entity.toCompletedModel() },
                            archived = task.archived.map { entity -> entity.toArchivedModel() },
                            priority = task.priority.map { entity -> entity.toPriorityModel() },
                            event = task.event?.toEventModel(),
                            links = task.links.map { entity -> entity.toLinkModel() },
                            tasks = children
                        )
                    }
                    emit(tasksWithChildren)
                }
            }
    }




}