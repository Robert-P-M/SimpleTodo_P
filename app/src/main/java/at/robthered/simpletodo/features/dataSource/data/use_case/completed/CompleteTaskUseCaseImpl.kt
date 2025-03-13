package at.robthered.simpletodo.features.dataSource.data.use_case.completed

import at.robthered.simpletodo.features.dataSource.domain.local.model.CompletedModel
import at.robthered.simpletodo.features.dataSource.domain.repository.completed.CompletedRepository
import at.robthered.simpletodo.features.dataSource.domain.use_case.completed.CompleteTaskUseCase

class CompleteTaskUseCaseImpl(
    private val completedRepository: CompletedRepository
) : CompleteTaskUseCase {
    override suspend fun invoke(completedModel: CompletedModel) {
        completedRepository.upsert(completedModel)
    }
}