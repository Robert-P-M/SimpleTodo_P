package at.robthered.simpletodo.features.dataSource.domain.repository.priority

import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityModel
import kotlinx.coroutines.flow.Flow

interface LocalPriorityRepository {
    suspend fun upsert(priorityModel: PriorityModel): Result<Unit, Error>
    fun getPrioritiesForTask(taskId: Long?): Flow<List<PriorityModel>>
    suspend fun delete()
    suspend fun delete(id: Long?)
    suspend fun delete(ids: List<Long?>)
    suspend fun deletePrioritiesOfTask(taskId: Long?)
}