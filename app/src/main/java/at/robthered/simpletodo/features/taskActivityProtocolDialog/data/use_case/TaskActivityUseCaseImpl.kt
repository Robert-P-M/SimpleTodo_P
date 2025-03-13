package at.robthered.simpletodo.features.taskActivityProtocolDialog.data.use_case

import at.robthered.simpletodo.features.core.domain.Resource
import at.robthered.simpletodo.features.core.presentation.UiText
import at.robthered.simpletodo.features.dataSource.domain.repository.archived.ArchivedRepository
import at.robthered.simpletodo.features.dataSource.domain.repository.completed.CompletedRepository
import at.robthered.simpletodo.features.dataSource.domain.repository.event.EventRepository
import at.robthered.simpletodo.features.dataSource.domain.repository.link.LinkRepository
import at.robthered.simpletodo.features.dataSource.domain.repository.priority.PriorityRepository
import at.robthered.simpletodo.features.dataSource.domain.repository.task.TaskRepository
import at.robthered.simpletodo.features.taskActivityProtocolDialog.domain.model.TaskActivityModel
import at.robthered.simpletodo.features.taskActivityProtocolDialog.domain.use_case.TaskActivityUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TaskActivityUseCaseImpl(
    private val archivedRepository: ArchivedRepository,
    private val completedRepository: CompletedRepository,
    private val eventRepository: EventRepository,
    private val priorityRepository: PriorityRepository,
    private val taskRepository: TaskRepository,
    private val taskListRepository: TaskRepository,
    private val linkRepository: LinkRepository,
) : TaskActivityUseCase {

    override operator fun invoke(taskId: Long?): Flow<Resource<List<TaskActivityModel>>> = flow {
        emit(Resource.Loading())

        try {
            val taskFlow = taskRepository.get(taskId).firstOrNull()
            val archivedFlow = archivedRepository.getArchivedForTask(taskId).first()
            val completedFlow = completedRepository.getCompletionsForTask(taskId).first()
            val eventFlow = eventRepository.getEventsForTask(taskId).first()
            val priorityFlow = priorityRepository.getPrioritiesForTask(taskId).first()
            val tasksFlow = taskListRepository.getChildTasks(taskId).first()
            val linksFlow = linkRepository.getLinksOfTask(taskId).first()

                val activities = mutableListOf<TaskActivityModel>()

                taskFlow?.let {
                    activities.add(TaskActivityModel.Task(task = it))
                }
                activities.addAll(archivedFlow.mapIndexed { index, archivedModel -> TaskActivityModel.Archived(archived = archivedModel, isFirst = index == 0) })
                activities.addAll(completedFlow.mapIndexed { index, completedModel -> TaskActivityModel.Completed(completed = completedModel, isFirst =  index == 0) })
                activities.addAll(eventFlow.mapIndexed { index, eventModel -> TaskActivityModel.Event(event = eventModel, isFirst =  index == 0) })
                activities.addAll(priorityFlow.mapIndexed { index, priorityModel ->  TaskActivityModel.Priority(priority = priorityModel, isFirst = index == 0) })
                activities.addAll(tasksFlow.mapIndexed { index, taskModel ->  TaskActivityModel.ChildTask(childTask = taskModel, isFirst = index == 0) })
                activities.addAll(linksFlow.mapIndexed { index, linkModel -> TaskActivityModel.Link(link = linkModel, isFirst = index == 0) })

                val sortedActivities = activities.sortedByDescending { it.createdAt }
            emit(Resource.Success(sortedActivities))

        } catch (e: Exception) {
            emit(
                Resource.Error(
                    UiText.DynamicString(
                        e.localizedMessage ?: "An unknown error occurred"
                    )
                )
            )
        }
    }.flowOn(Dispatchers.Default)
}