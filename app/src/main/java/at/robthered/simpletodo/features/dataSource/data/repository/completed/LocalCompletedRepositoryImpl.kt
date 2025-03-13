package at.robthered.simpletodo.features.dataSource.data.repository.completed

import at.robthered.simpletodo.features.dataSource.domain.repository.completed.LocalCompletedRepository
import at.robthered.simpletodo.features.dataSource.domain.local.model.CompletedModel
import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.core.utils.safeCall
import at.robthered.simpletodo.features.dataSource.data.local.dao.completed.CompletedDao
import at.robthered.simpletodo.features.dataSource.data.local.mapper.toCompletedEntity
import at.robthered.simpletodo.features.dataSource.data.local.mapper.toCompletedModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalCompletedRepositoryImpl(
    private val completedDao: CompletedDao,
) : LocalCompletedRepository {

    override suspend fun upsert(completedModel: CompletedModel): Result<Unit, Error> {
        return safeCall {
            completedDao.upsert(completedEntity = completedModel.toCompletedEntity())

        }
    }

    override fun getCompletionsForTask(taskId: Long?): Flow<List<CompletedModel>> {
        return completedDao.getCompletionsForTask(taskId = taskId)
            .map { entities -> entities.map { it.toCompletedModel() }   }
    }

    override suspend fun delete() {
        completedDao.delete()
    }

    override suspend fun delete(id: Long?) {
        completedDao.delete(id)
    }

    override suspend fun delete(ids: List<Long?>) {
        completedDao.delete(ids)
    }

    override suspend fun deleteCompletedOfTask(taskId: Long?) {
        completedDao.deleteCompletedOfTask(taskId)
    }
}