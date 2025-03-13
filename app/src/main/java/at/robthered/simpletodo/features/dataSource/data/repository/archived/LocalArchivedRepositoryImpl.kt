package at.robthered.simpletodo.features.dataSource.data.repository.archived

import at.robthered.simpletodo.features.dataSource.domain.repository.archived.LocalArchivedRepository
import at.robthered.simpletodo.features.dataSource.domain.local.model.ArchivedModel
import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.core.utils.safeCall
import at.robthered.simpletodo.features.dataSource.data.local.dao.archived.ArchivedDao
import at.robthered.simpletodo.features.dataSource.data.local.mapper.toArchivedEntity
import at.robthered.simpletodo.features.dataSource.data.local.mapper.toArchivedModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalArchivedRepositoryImpl(
    private val archivedDao: ArchivedDao,
) : LocalArchivedRepository {
    override suspend fun upsert(archivedModel: ArchivedModel): Result<Unit, Error> {
        return safeCall {
            archivedDao.upsert(archivedEntity = archivedModel.toArchivedEntity())
        }
    }

    override fun getArchivedForTask(taskId: Long?): Flow<List<ArchivedModel>> {
        return archivedDao.getArchivedOfTask(taskId)
            .map { entities -> entities.map { it.toArchivedModel() } }
    }

    override suspend fun delete() {
        archivedDao.delete()
    }

    override suspend fun delete(id: Long?) {
        archivedDao.delete(id)
    }

    override suspend fun delete(ids: List<Long?>) {
        archivedDao.delete(ids)
    }

    override suspend fun deleteArchivedOfTask(taskId: Long?) {
        archivedDao.deleteArchivedOfTask(taskId)
    }
}