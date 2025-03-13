package at.robthered.simpletodo.features.dataSource.domain.repository.completed

import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.dataSource.domain.local.model.CompletedModel
import kotlinx.coroutines.flow.Flow

interface CompletedRepository {
    suspend fun upsert(completedModel: CompletedModel): Result<Unit, Error>
    fun getCompletionsForTask(taskId: Long?): Flow<List<CompletedModel>>
    suspend fun delete()
    suspend fun delete(id: Long?)
    suspend fun delete(ids: List<Long?>)
    suspend fun deleteCompletedOfTask(taskId: Long?)
}