package at.robthered.simpletodo.features.dataSource.data.use_case

import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.dataSource.data.ext.clearStatus
import at.robthered.simpletodo.features.dataSource.domain.local.model.EventModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.LinkModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.TaskModel
import at.robthered.simpletodo.features.dataSource.domain.local.relation.TaskWithDetailsModel
import at.robthered.simpletodo.features.dataSource.domain.repository.taskWithDetails.TaskWithDetailsRepository
import at.robthered.simpletodo.features.dataSource.domain.use_case.UpdateTaskWithDetailsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateTaskWithDetailsUseCaseImpl(
    private val taskWithDetailsRepository: TaskWithDetailsRepository,
    ): UpdateTaskWithDetailsUseCase {
    override suspend operator fun invoke(
        taskModel: TaskModel,
        priorityModel: PriorityModel,
        eventModel: EventModel?,
        linkModels: List<LinkModel>,
    ): Result<Unit, Error> = withContext(Dispatchers.IO) {
        try {

            val taskWithDetails = TaskWithDetailsModel(
                task = taskModel,
                priority = listOf(priorityModel.clearStatus()),
                event = eventModel?.clearStatus(),
                links = linkModels
            )

            taskWithDetailsRepository.upsert(taskWithDetailsModel = taskWithDetails)

        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}