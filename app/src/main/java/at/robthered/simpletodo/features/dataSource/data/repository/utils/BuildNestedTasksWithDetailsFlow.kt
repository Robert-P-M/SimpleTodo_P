package at.robthered.simpletodo.features.dataSource.data.repository.utils

import at.robthered.simpletodo.features.dataSource.domain.local.relation.TaskWithDetailsAndChildrenModel
import at.robthered.simpletodo.features.dataSource.domain.local.relation.TaskWithDetailsModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalCoroutinesApi::class)
fun buildNestedTasksWithDetailsFlow(
    taskId: Long?,
    depth: Int,
    getTasksOfParentWithDetails: (taskId: Long?) -> Flow<List<TaskWithDetailsModel>>,
): Flow<List<TaskWithDetailsAndChildrenModel>> {
    if(depth == 0) return flowOf(emptyList())

    return getTasksOfParentWithDetails(taskId).flatMapLatest { tasks ->
        flow {
            val taskWithDetailsList = tasks.map { task ->
                val childrenFlow = buildNestedTasksWithDetailsFlow(
                    taskId = task.task.taskId,
                    depth = depth - 1,
                    getTasksOfParentWithDetails = getTasksOfParentWithDetails
                )

                val children = childrenFlow.first()
                TaskWithDetailsAndChildrenModel(
                    task = task.task,
                    completed = task.completed,
                    archived = task.archived,
                    priority = task.priority,
                    event = task.event,
                    links = task.links,
                    tasks = children
                )
            }
            emit(taskWithDetailsList)
        }
    }
}