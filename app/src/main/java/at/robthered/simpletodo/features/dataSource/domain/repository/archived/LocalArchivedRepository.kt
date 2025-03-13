package at.robthered.simpletodo.features.dataSource.domain.repository.archived

import at.robthered.simpletodo.features.dataSource.domain.local.model.ArchivedModel
import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import kotlinx.coroutines.flow.Flow

interface LocalArchivedRepository {

    suspend fun upsert(archivedModel: ArchivedModel): Result<Unit, Error>
    fun getArchivedForTask(taskId: Long?): Flow<List<ArchivedModel>>
    suspend fun delete()
    suspend fun delete(id: Long?)
    suspend fun delete(ids: List<Long?>)
    suspend fun deleteArchivedOfTask(taskId: Long?)
}