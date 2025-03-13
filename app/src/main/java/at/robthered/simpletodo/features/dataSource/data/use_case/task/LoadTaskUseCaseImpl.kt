package at.robthered.simpletodo.features.dataSource.data.use_case.task

import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.domain.Resource
import at.robthered.simpletodo.features.core.presentation.UiText
import at.robthered.simpletodo.features.dataSource.domain.local.relation.TaskWithDetailsAndChildrenModel
import at.robthered.simpletodo.features.dataSource.domain.repository.taskWithDetails.TaskWithDetailsRepository
import at.robthered.simpletodo.features.dataSource.domain.use_case.task.LoadTaskUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class LoadTaskUseCaseImpl(
    private val taskWithDetailsRepository: TaskWithDetailsRepository,
) : LoadTaskUseCase {
    override operator fun invoke(
        taskId: Long?,
        depth: Int,
    ): Flow<Resource<TaskWithDetailsAndChildrenModel>> {
        return taskWithDetailsRepository.getTaskWithDetails(taskId, depth)
            .map { item ->
                item?.let {
                    Resource.Success(it)
                } ?:  Resource.Error(UiText.StringResourceId(R.string.error_task_details_dialog_no_task_found))

            }
            .onStart {
                emit(Resource.Loading())
            }
    }

    override operator fun invoke(taskId: Long?): Flow<Resource<TaskWithDetailsAndChildrenModel>> {
        return taskWithDetailsRepository.getTaskWithDetails(taskId, 0).map { item ->
            item?.let {
                Resource.Success(it)
            } ?: Resource.Error(UiText.StringResourceId(R.string.error_task_details_dialog_no_task_found))
        }
            .onStart {
                emit(Resource.Loading())
            }
    }
}