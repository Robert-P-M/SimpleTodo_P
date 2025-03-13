package at.robthered.simpletodo.features.dataSource.domain.repository.eventToTask

import at.robthered.simpletodo.features.dataSource.data.local.relation.EventToTaskRelation
import at.robthered.simpletodo.features.dataSource.domain.local.relation.EventToTaskModel
import kotlinx.coroutines.flow.Flow

interface LocalEventToTaskRepository {
    fun get(): Flow<List<EventToTaskModel>>
}