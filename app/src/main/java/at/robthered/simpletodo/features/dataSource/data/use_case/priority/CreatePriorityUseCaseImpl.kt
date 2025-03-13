package at.robthered.simpletodo.features.dataSource.data.use_case.priority

import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.dataSource.data.ext.clearStatus
import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityModel
import at.robthered.simpletodo.features.dataSource.domain.repository.priority.PriorityRepository
import at.robthered.simpletodo.features.dataSource.domain.use_case.priority.CreatePriorityUseCase

class CreatePriorityUseCaseImpl(
    private val priorityRepository: PriorityRepository
): CreatePriorityUseCase {
    override suspend operator fun invoke(priorityModel: PriorityModel): Result<Unit, Error> {
        return priorityRepository.upsert(priorityModel.clearStatus())
    }
}