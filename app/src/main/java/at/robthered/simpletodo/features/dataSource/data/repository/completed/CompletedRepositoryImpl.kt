package at.robthered.simpletodo.features.dataSource.data.repository.completed

import at.robthered.simpletodo.features.dataSource.domain.repository.completed.LocalCompletedRepository
import at.robthered.simpletodo.features.dataSource.domain.local.model.CompletedModel
import at.robthered.simpletodo.features.dataSource.domain.repository.completed.CompletedRepository
import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import kotlinx.coroutines.flow.Flow

class CompletedRepositoryImpl(
    private val localCompletedRepository: LocalCompletedRepository,
) : CompletedRepository {
    override suspend fun upsert(completedModel: CompletedModel): Result<Unit, Error> {
        return localCompletedRepository.upsert(completedModel)
    }

    override fun getCompletionsForTask(taskId: Long?): Flow<List<CompletedModel>> {
        return localCompletedRepository.getCompletionsForTask(taskId)
    }

    override suspend fun delete() {
        localCompletedRepository.delete()
    }

    override suspend fun delete(id: Long?) {
        localCompletedRepository.delete(id)
    }

    override suspend fun delete(ids: List<Long?>) {
        localCompletedRepository.delete(ids)
    }

    override suspend fun deleteCompletedOfTask(taskId: Long?) {
        localCompletedRepository.deleteCompletedOfTask(taskId)
    }
}