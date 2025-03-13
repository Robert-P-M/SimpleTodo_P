package at.robthered.simpletodo.features.dataSource.data.repository.eventToTask

import at.robthered.simpletodo.features.dataSource.data.local.relation.EventToTaskRelation
import at.robthered.simpletodo.features.dataSource.domain.local.relation.EventToTaskModel
import at.robthered.simpletodo.features.dataSource.domain.repository.eventToTask.EventToTaskRepository
import at.robthered.simpletodo.features.dataSource.domain.repository.eventToTask.LocalEventToTaskRepository
import kotlinx.coroutines.flow.Flow

class EventToTaskRepositoryImpl(
    private val localEventToTaskRepository: LocalEventToTaskRepository
) : EventToTaskRepository {
    override fun get(): Flow<List<EventToTaskModel>> {
        return localEventToTaskRepository.get()
    }
}