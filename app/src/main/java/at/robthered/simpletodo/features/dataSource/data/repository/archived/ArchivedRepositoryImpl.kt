package at.robthered.simpletodo.features.dataSource.data.repository.archived

import at.robthered.simpletodo.features.dataSource.domain.repository.archived.LocalArchivedRepository
import at.robthered.simpletodo.features.dataSource.domain.local.model.ArchivedModel
import at.robthered.simpletodo.features.dataSource.domain.repository.archived.ArchivedRepository
import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import kotlinx.coroutines.flow.Flow

class ArchivedRepositoryImpl(
    private val localArchivedRepository: LocalArchivedRepository,
) : ArchivedRepository {
    override suspend fun upsert(archivedModel: ArchivedModel): Result<Unit, Error> {
        return localArchivedRepository.upsert(archivedModel)
    }

    override fun getArchivedForTask(taskId: Long?): Flow<List<ArchivedModel>> {
        return localArchivedRepository.getArchivedForTask(taskId)
    }

    override suspend fun delete() {
        localArchivedRepository.delete()
    }

    override suspend fun delete(id: Long?) {
        localArchivedRepository.delete(id)
    }

    override suspend fun delete(ids: List<Long?>) {
        localArchivedRepository.delete(ids)
    }

    override suspend fun deleteArchivedOfTask(taskId: Long?) {
        localArchivedRepository.deleteArchivedOfTask(taskId)
    }
}