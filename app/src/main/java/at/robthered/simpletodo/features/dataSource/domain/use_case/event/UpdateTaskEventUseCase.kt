package at.robthered.simpletodo.features.dataSource.domain.use_case.event

import at.robthered.simpletodo.features.dataSource.domain.local.model.EventModel

interface UpdateTaskEventUseCase {
    suspend operator fun invoke(eventModel: EventModel, taskId: Long?)
}