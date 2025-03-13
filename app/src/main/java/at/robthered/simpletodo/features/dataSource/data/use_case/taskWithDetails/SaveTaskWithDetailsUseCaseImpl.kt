package at.robthered.simpletodo.features.dataSource.data.use_case.taskWithDetails

import android.util.Log
import at.robthered.simpletodo.features.alarm.domain.ScheduleAlarmUseCase
import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.core.utils.onSuccess
import at.robthered.simpletodo.features.dataSource.data.repository.taskWithDetails.InsertTaskWithDetailsResult
import at.robthered.simpletodo.features.dataSource.domain.local.model.EventModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.LinkModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.TaskModel
import at.robthered.simpletodo.features.dataSource.domain.local.relation.TaskWithDetailsModel
import at.robthered.simpletodo.features.dataSource.domain.repository.taskWithDetails.TaskWithDetailsRepository
import at.robthered.simpletodo.features.dataSource.domain.use_case.taskWithDetails.SaveTaskWithDetailsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SaveTaskWithDetailsUseCaseImpl(
    private val taskWithDetailsRepository: TaskWithDetailsRepository,
    private val scheduleAlarmUseCase: ScheduleAlarmUseCase,
) : SaveTaskWithDetailsUseCase {
    override suspend operator fun invoke(
        taskModel: TaskModel,
        priorityModel: PriorityModel,
        eventModel: EventModel?,
        linkModels: List<LinkModel>,
    ): Result<InsertTaskWithDetailsResult, Error> = withContext(Dispatchers.IO) {

        try {

            val taskWithDetails = TaskWithDetailsModel(
                task = taskModel,
                priority = listOf(priorityModel),
                event = eventModel,
                links = linkModels
            )


            taskWithDetailsRepository.insert(taskWithDetailsModel = taskWithDetails)
                .onSuccess { insertTaskWithDetailsResult ->
                    insertTaskWithDetailsResult.event?.let { event ->
                        event.eventId?.let {
                            scheduleAlarmUseCase(eventId = it, triggerAtMillis = event.start.toEpochMilliseconds())
                        }
                    }
                }




        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}