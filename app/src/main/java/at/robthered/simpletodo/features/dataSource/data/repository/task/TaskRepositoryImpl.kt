package at.robthered.simpletodo.features.dataSource.data.repository.task

import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.dataSource.domain.repository.task.LocalTaskRepository
import at.robthered.simpletodo.features.dataSource.domain.local.model.TaskModel
import at.robthered.simpletodo.features.dataSource.domain.repository.task.TaskRepository
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(
    private val localTaskRepository: LocalTaskRepository,
) : TaskRepository {
    override suspend fun upsert(item: TaskModel): Result<Unit, Error> {
        return localTaskRepository.upsert(item)
    }

    override suspend fun upsert(items: List<TaskModel>): Result<Unit, Error> {
        return localTaskRepository.upsert(items)
    }

    override fun get(): Flow<List<TaskModel>> {
        return localTaskRepository.get()
    }

    override fun get(id: Long?): Flow<TaskModel?> {
        return localTaskRepository.get(id)
    }

    override suspend fun delete(id: Long?): Result<Unit, Error> {
        return localTaskRepository.delete(id)
    }

    override suspend fun delete(ids: List<Long>): Result<Unit, Error> {
        return localTaskRepository.delete(ids)
    }

    override suspend fun delete(): Result<Unit, Error> {
        return localTaskRepository.delete()
    }

    override fun getTasksWithoutSection(): Flow<List<TaskModel>> {
        return localTaskRepository.getTasksWithoutSection()
    }

    override fun getChildTasks(parentTaskId: Long?): Flow<List<TaskModel>> {
        return localTaskRepository.getChildTasks(parentTaskId)
    }

    override fun getTaskOfEvent(eventId: Long?): Flow<TaskModel?> {
        return localTaskRepository.getTaskOfEvent(eventId)
    }
}