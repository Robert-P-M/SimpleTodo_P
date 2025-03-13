package at.robthered.simpletodo.features.dataSource.data.use_case.task

import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.dataSource.domain.repository.task.TaskRepository
import at.robthered.simpletodo.features.dataSource.domain.use_case.task.DeleteTaskUseCase

class DeleteTaskUseCaseImpl(
    private val taskRepository: TaskRepository
) : DeleteTaskUseCase {
    override suspend fun invoke(taskId: Long?): Result<Unit, Error> {
        return taskRepository.delete(id = taskId)

    }
}