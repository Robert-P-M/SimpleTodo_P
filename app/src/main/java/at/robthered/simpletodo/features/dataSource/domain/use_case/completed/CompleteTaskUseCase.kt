package at.robthered.simpletodo.features.dataSource.domain.use_case.completed

import at.robthered.simpletodo.features.dataSource.domain.local.model.CompletedModel

interface CompleteTaskUseCase {
    suspend operator fun invoke(completedModel: CompletedModel)
}