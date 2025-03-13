package at.robthered.simpletodo.features.taskActivityProtocolDialog.domain.use_case

import at.robthered.simpletodo.features.core.domain.Resource
import at.robthered.simpletodo.features.taskActivityProtocolDialog.domain.model.TaskActivityModel
import kotlinx.coroutines.flow.Flow

interface TaskActivityUseCase {
    operator fun invoke(taskId: Long?): Flow<Resource<List<TaskActivityModel>>>
}