package at.robthered.simpletodo.features.dataSource.domain.repository.task

import at.robthered.simpletodo.features.dataSource.data.local.entity.TaskEntity
import at.robthered.simpletodo.features.dataSource.domain.local.model.TaskModel
import at.robthered.simpletodo.features.dataSource.domain.repository.base.BaseLocalRepository
import kotlinx.coroutines.flow.Flow

interface LocalTaskRepository : BaseLocalRepository<TaskModel> {
    fun getTasksWithoutSection(): Flow<List<TaskModel>>
    fun getChildTasks(parentTaskId: Long?): Flow<List<TaskModel>>
    fun getTaskOfEvent(
        eventId: Long?
    ): Flow<TaskModel?>
}