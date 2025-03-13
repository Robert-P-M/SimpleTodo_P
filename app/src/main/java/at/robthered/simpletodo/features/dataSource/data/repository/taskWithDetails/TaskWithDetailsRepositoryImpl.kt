package at.robthered.simpletodo.features.dataSource.data.repository.taskWithDetails

import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.dataSource.data.local.mapper.toTaskModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.EventModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.TaskModel
import at.robthered.simpletodo.features.dataSource.domain.local.relation.TaskWithDetailsAndChildrenModel
import at.robthered.simpletodo.features.dataSource.domain.local.relation.TaskWithDetailsModel
import at.robthered.simpletodo.features.dataSource.domain.repository.taskWithDetails.LocalTaskWithDetailsRepository
import at.robthered.simpletodo.features.dataSource.domain.repository.taskWithDetails.TaskWithDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskWithDetailsRepositoryImpl(
    private val localTaskWithDetailsRepository: LocalTaskWithDetailsRepository
) : TaskWithDetailsRepository {
    override fun get(): Flow<List<TaskWithDetailsModel>> {
        return localTaskWithDetailsRepository.get()
    }

    override fun get(taskId: Long?): Flow<TaskWithDetailsModel?> {
        return localTaskWithDetailsRepository.get(taskId)
    }

    override fun getTasksOfParentWithDetails(parentTaskId: Long?): Flow<List<TaskWithDetailsModel>> {
        return localTaskWithDetailsRepository.getTasksOfParentWithDetails(parentTaskId)
    }

    override fun getTaskWithDetails(
        taskId: Long?,
        depth: Int,
    ): Flow<TaskWithDetailsAndChildrenModel?> {
        return localTaskWithDetailsRepository.getTaskWithDetails(taskId, depth)
    }

    override suspend fun updateTaskEvent(
        newEventModel: EventModel,
        taskId: Long?
    ): Result<Unit, Error> {
        return localTaskWithDetailsRepository.updateTaskEvent(newEventModel, taskId)
    }

    override fun getAllTasksWithDetails(depth: Int): Flow<List<TaskWithDetailsAndChildrenModel>> {
        return localTaskWithDetailsRepository.getAllTasksWithDetails()
    }

    override suspend fun insert(taskWithDetailsModel: TaskWithDetailsModel): Result<InsertTaskWithDetailsResult, Error> {
        return localTaskWithDetailsRepository.insert(taskWithDetailsModel)
    }
    override suspend fun upsert(taskWithDetailsModel: TaskWithDetailsModel): Result<Unit, Error> {
        return localTaskWithDetailsRepository.upsert(taskWithDetailsModel)
    }

}