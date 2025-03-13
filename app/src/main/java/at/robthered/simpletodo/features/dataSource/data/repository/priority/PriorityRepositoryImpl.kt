package at.robthered.simpletodo.features.dataSource.data.repository.priority

import at.robthered.simpletodo.features.dataSource.domain.repository.priority.LocalPriorityRepository
import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityModel
import at.robthered.simpletodo.features.dataSource.domain.repository.priority.PriorityRepository
import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import kotlinx.coroutines.flow.Flow

class PriorityRepositoryImpl(
    private val localPriorityRepository: LocalPriorityRepository,
) : PriorityRepository {
    override suspend fun upsert(priorityModel: PriorityModel): Result<Unit, Error> {
        return localPriorityRepository.upsert(priorityModel)
    }

    override fun getPrioritiesForTask(taskId: Long?): Flow<List<PriorityModel>> {
        return localPriorityRepository.getPrioritiesForTask(taskId)
    }

    override suspend fun delete() {
        localPriorityRepository.delete()
    }

    override suspend fun delete(id: Long?) {
        localPriorityRepository.delete(id)
    }

    override suspend fun delete(ids: List<Long?>) {
        localPriorityRepository.delete(ids)
    }

    override suspend fun deletePrioritiesOfTask(taskId: Long?) {
        localPriorityRepository.deletePrioritiesOfTask(taskId)
    }
}