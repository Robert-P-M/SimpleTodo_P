package at.robthered.simpletodo.features.homeScreen.data.use_case

import at.robthered.simpletodo.features.core.domain.Resource
import at.robthered.simpletodo.features.core.presentation.UiText
import at.robthered.simpletodo.features.dataSource.data.ext.toScreenItemModel
import at.robthered.simpletodo.features.dataSource.data.ext.toScreenItemModels
import at.robthered.simpletodo.features.dataSource.domain.repository.sectionWithTasks.SectionWithTasksWithDetailsRepository
import at.robthered.simpletodo.features.dataSource.domain.repository.taskWithDetails.TaskWithDetailsRepository
import at.robthered.simpletodo.features.homeScreen.domain.model.ScreenItemsModel
import at.robthered.simpletodo.features.homeScreen.domain.use_case.LoadHomeItemsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

class LoadHomeItemsUseCaseImpl(
    private val taskWithDetailsRepository: TaskWithDetailsRepository,
    private val sectionWithTasksWithDetailsRepository: SectionWithTasksWithDetailsRepository
) : LoadHomeItemsUseCase {
    override fun invoke(depth: Int): Flow<Resource<List<ScreenItemsModel>>> = flow {

        emit(Resource.Loading())

        try {
            val sectionsFlow = sectionWithTasksWithDetailsRepository.getSectionsWithTasksWithDetails(depth = depth)
            val tasksFlow = taskWithDetailsRepository.getAllTasksWithDetails(depth = depth)
            combine(
                sectionsFlow,
                tasksFlow
            ) { sectionsRes, tasksRes ->
                val items = listOf(tasksRes.toScreenItemModel()) + sectionsRes.toScreenItemModels()
                Resource.Success(items)
            }.collect { result ->
                emit(result)
            }
        } catch (e: Exception) {
            emit(Resource.Error(UiText.DynamicString(e.message ?: "Unknown error")))
        }

    }
}