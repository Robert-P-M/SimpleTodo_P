package at.robthered.simpletodo.features.dataSource.domain.use_case

import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.dataSource.domain.local.model.EventModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.LinkModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.TaskModel

interface UpdateTaskWithDetailsUseCase {
    suspend operator fun invoke(
        taskModel: TaskModel,
        priorityModel: PriorityModel,
        eventModel: EventModel?,
        linkModels: List<LinkModel>
    ): Result<Unit, Error>
}