package at.robthered.simpletodo.features.dataSource.domain.use_case.priority

import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityModel

interface CreatePriorityUseCase {
    suspend operator fun invoke(priorityModel: PriorityModel): Result<Unit, Error>
}