package at.robthered.simpletodo.features.dataSource.domain.use_case.task

import at.robthered.simpletodo.features.core.domain.Resource
import at.robthered.simpletodo.features.dataSource.domain.local.relation.TaskWithDetailsAndChildrenModel
import kotlinx.coroutines.flow.Flow

interface LoadTaskUseCase {
    operator fun invoke(taskId: Long?, depth: Int): Flow<Resource<TaskWithDetailsAndChildrenModel>>
    operator fun invoke(taskId: Long?): Flow<Resource<TaskWithDetailsAndChildrenModel>>
}