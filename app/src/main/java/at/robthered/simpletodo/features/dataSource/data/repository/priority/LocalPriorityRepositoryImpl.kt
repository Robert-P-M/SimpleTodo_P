package at.robthered.simpletodo.features.dataSource.data.repository.priority

import at.robthered.simpletodo.features.dataSource.domain.repository.priority.LocalPriorityRepository
import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityModel
import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.core.utils.safeCall
import at.robthered.simpletodo.features.dataSource.data.local.dao.priority.PriorityDao
import at.robthered.simpletodo.features.dataSource.data.local.mapper.toPriorityEntity
import at.robthered.simpletodo.features.dataSource.data.local.mapper.toPriorityModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalPriorityRepositoryImpl(
    private val priorityDao: PriorityDao,
) : LocalPriorityRepository {
    override suspend fun upsert(priorityModel: PriorityModel): Result<Unit, Error> {
        return safeCall {
            priorityDao.upsertPriority(priorityEntity = priorityModel.toPriorityEntity())
        }
    }

    override fun getPrioritiesForTask(taskId: Long?): Flow<List<PriorityModel>> {
        return priorityDao.getPrioriesForTask(taskId = taskId)
            .map { entities -> entities.map { it.toPriorityModel() } }
    }

    override suspend fun delete() {
        priorityDao.delete()
    }

    override suspend fun delete(id: Long?) {
        priorityDao.delete(id)
    }

    override suspend fun delete(ids: List<Long?>) {
        priorityDao.delete(ids)
    }

    override suspend fun deletePrioritiesOfTask(taskId: Long?) {
        priorityDao.deletePrioritiesOfTask(taskId)
    }
}