package at.robthered.simpletodo.features.dataSource.domain.use_case.task

import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result

interface DeleteTaskUseCase {
    suspend operator fun invoke(taskId: Long?): Result<Unit, Error>
}