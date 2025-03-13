package at.robthered.simpletodo.features.dataSource.domain.repository.taskWithDetails

import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.dataSource.data.repository.taskWithDetails.InsertTaskWithDetailsResult
import at.robthered.simpletodo.features.dataSource.domain.local.model.EventModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.TaskModel
import at.robthered.simpletodo.features.dataSource.domain.local.relation.TaskWithDetailsAndChildrenModel
import at.robthered.simpletodo.features.dataSource.domain.local.relation.TaskWithDetailsModel
import kotlinx.coroutines.flow.Flow

interface LocalTaskWithDetailsRepository {
    fun get(): Flow<List<TaskWithDetailsModel>>
    fun get(taskId: Long?): Flow<TaskWithDetailsModel?>
    fun getTasksOfParentWithDetails(parentTaskId: Long?): Flow<List<TaskWithDetailsModel>>
    suspend fun updateTaskEvent(
        newEventModel: EventModel,
        taskId: Long?
    ): Result<Unit, Error>
    fun getTaskWithDetails(
        taskId: Long?,
        depth: Int = 5,
    ): Flow<TaskWithDetailsAndChildrenModel?>
    fun getAllTasksWithDetails(depth: Int = 5): Flow<List<TaskWithDetailsAndChildrenModel>>
    suspend fun insert(
        taskWithDetailsModel: TaskWithDetailsModel
    ): Result<InsertTaskWithDetailsResult, Error>
    suspend fun upsert(
        taskWithDetailsModel: TaskWithDetailsModel
    ): Result<Unit, Error>
}