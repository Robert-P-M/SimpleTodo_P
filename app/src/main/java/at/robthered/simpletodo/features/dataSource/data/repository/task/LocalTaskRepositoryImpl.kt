package at.robthered.simpletodo.features.dataSource.data.repository.task

import at.robthered.simpletodo.features.dataSource.domain.repository.task.LocalTaskRepository
import at.robthered.simpletodo.features.dataSource.domain.local.model.TaskModel
import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.core.utils.safeCall
import at.robthered.simpletodo.features.dataSource.data.local.dao.task.TaskDao
import at.robthered.simpletodo.features.dataSource.data.local.mapper.toTaskEntity
import at.robthered.simpletodo.features.dataSource.data.local.mapper.toTaskModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalTaskRepositoryImpl(
    private val taskDao: TaskDao,
) : LocalTaskRepository {
    override suspend fun upsert(item: TaskModel): Result<Unit, Error> {
        return safeCall {
            taskDao.upsertTask(taskEntity = item.toTaskEntity())
        }
    }

    override suspend fun upsert(items: List<TaskModel>): Result<Unit, Error> {
        return safeCall {
            taskDao.upsertTasks(taskEntities = items.map { it.toTaskEntity() })
        }
    }

    override fun get(): Flow<List<TaskModel>> {
        return taskDao.getAllTasks().map { entities -> entities.map { it.toTaskModel() } }
    }

    override fun get(id: Long?): Flow<TaskModel?> {
        return taskDao.getTask(taskId = id).map { it?.toTaskModel() }
    }

    override suspend fun delete(id: Long?): Result<Unit, Error> {
        return safeCall {
            taskDao.deleteTask(taskId = id)
        }
    }

    override suspend fun delete(ids: List<Long>): Result<Unit, Error> {
        return safeCall {
            taskDao.deleteMultipleTasks(taskIds = ids)
        }
    }

    override suspend fun delete(): Result<Unit, Error> {
        return safeCall {
            taskDao.deleteAllTasks()
        }
    }

    override fun getTasksWithoutSection(): Flow<List<TaskModel>> {
        return taskDao.getAllTasksWithoutSection()
            .map { entities -> entities.map { it.toTaskModel() } }
    }

    override fun getChildTasks(parentTaskId: Long?): Flow<List<TaskModel>> {
        return taskDao.getChildTasks(parentTaskId).map { entities -> entities.map { it.toTaskModel() } }
    }

    override fun getTaskOfEvent(eventId: Long?): Flow<TaskModel?> {
        return taskDao.getTaskOfEvent(eventId).map { it?.toTaskModel() }
    }

}