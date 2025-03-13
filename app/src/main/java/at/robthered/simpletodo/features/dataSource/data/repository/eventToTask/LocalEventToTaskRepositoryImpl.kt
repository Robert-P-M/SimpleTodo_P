package at.robthered.simpletodo.features.dataSource.data.repository.eventToTask

import at.robthered.simpletodo.features.dataSource.data.local.dao.eventToTask.EventToTaskDao
import at.robthered.simpletodo.features.dataSource.data.local.mapper.toModel
import at.robthered.simpletodo.features.dataSource.data.local.relation.EventToTaskRelation
import at.robthered.simpletodo.features.dataSource.domain.local.relation.EventToTaskModel
import at.robthered.simpletodo.features.dataSource.domain.repository.eventToTask.LocalEventToTaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalEventToTaskRepositoryImpl(
    private val eventToTaskDao: EventToTaskDao
) : LocalEventToTaskRepository {
    override fun get(): Flow<List<EventToTaskModel>> {
        return eventToTaskDao.get().map { relations -> relations.map { it.toModel() } }
    }
}