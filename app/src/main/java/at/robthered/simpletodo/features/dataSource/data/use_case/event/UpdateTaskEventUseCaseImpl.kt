package at.robthered.simpletodo.features.dataSource.data.use_case.event

import at.robthered.simpletodo.features.core.domain.eventBus.clear
import at.robthered.simpletodo.features.core.domain.eventBus.events.DueEvent
import at.robthered.simpletodo.features.core.domain.eventBus.events.DueEventBus
import at.robthered.simpletodo.features.core.utils.onSuccess
import at.robthered.simpletodo.features.dataSource.domain.local.model.EventModel
import at.robthered.simpletodo.features.dataSource.domain.repository.taskWithDetails.TaskWithDetailsRepository
import at.robthered.simpletodo.features.dataSource.domain.use_case.event.UpdateTaskEventUseCase

class UpdateTaskEventUseCaseImpl(
    private val taskWithDetailsRepository: TaskWithDetailsRepository,
    private val dueEventBus: DueEventBus
) : UpdateTaskEventUseCase {
    override suspend operator fun invoke(eventModel: EventModel, taskId: Long?) {
        val newEventModel = eventModel.copy(taskId = taskId)
        taskWithDetailsRepository.updateTaskEvent(newEventModel, taskId)
            .onSuccess {
                dueEventBus.clear(DueEvent.ClearEvent)
            }
    }
}