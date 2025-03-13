package at.robthered.simpletodo.features.dataSource.data.repository.event

import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.core.utils.safeCall
import at.robthered.simpletodo.features.dataSource.data.local.dao.event.EventDao
import at.robthered.simpletodo.features.dataSource.data.local.entity.EventEntity
import at.robthered.simpletodo.features.dataSource.data.local.mapper.toEventEntity
import at.robthered.simpletodo.features.dataSource.data.local.mapper.toEventModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.EventModel
import at.robthered.simpletodo.features.dataSource.domain.repository.event.LocalEventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalEventRepositoryImpl(
    private val eventDao: EventDao
) : LocalEventRepository {
    override suspend fun upsert(eventModel: EventModel): Result<Long, Error> {
        return safeCall {
            eventDao.upsert(entity = eventModel.toEventEntity())
        }
    }

    override fun get(eventId: Long?): Flow<EventEntity?> {
        return eventDao.get(eventId)
    }
    override fun getEventsForTask(taskId: Long?): Flow<List<EventModel>> {
        return eventDao.getEventsOfTask(taskId)
            .map { entities -> entities.map { it.toEventModel() } }
    }

    override suspend fun upsert(eventModels: List<EventModel>): Result<Unit, Error> {
        return safeCall {
            eventDao.upsert(entities = eventModels.map { it.toEventEntity() })
        }
    }

    override suspend fun delete() {
        eventDao.delete()
    }

    override suspend fun delete(id: Long?) {
        eventDao.delete(id)
    }

    override suspend fun delete(ids: List<Long?>) {
        eventDao.delete(ids)
    }

    override suspend fun deleteEventsOfTask(taskId: Long?) {
        eventDao.deleteEventsOfTask(taskId)
    }

    override suspend fun toggleNotification(eventId: Long?, isNotificationEnabled: Boolean) {
        eventDao.toggleNotification(eventId, isNotificationEnabled)
    }
}