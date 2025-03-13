package at.robthered.simpletodo.features.dataSource.data.repository.event

import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.dataSource.data.local.entity.EventEntity
import at.robthered.simpletodo.features.dataSource.domain.local.model.EventModel
import at.robthered.simpletodo.features.dataSource.domain.repository.event.EventRepository
import at.robthered.simpletodo.features.dataSource.domain.repository.event.LocalEventRepository
import kotlinx.coroutines.flow.Flow

class EventRepositoryImpl(
    private val localEventRepository: LocalEventRepository
) : EventRepository {
    override suspend fun upsert(eventModel: EventModel): Result<Long, Error> {
        return localEventRepository.upsert(eventModel)
    }

    override fun get(eventId: Long?): Flow<EventEntity?> {
        return localEventRepository.get(eventId)
    }
    override fun getEventsForTask(taskId: Long?): Flow<List<EventModel>> {
        return localEventRepository.getEventsForTask(taskId)
    }

    override suspend fun delete() {
        localEventRepository.delete()
    }

    override suspend fun delete(id: Long?) {
        localEventRepository.delete(id)
    }

    override suspend fun delete(ids: List<Long?>) {
        localEventRepository.delete(ids)
    }

    override suspend fun deleteEventsOfTask(taskId: Long?) {
        localEventRepository.deleteEventsOfTask(taskId)
    }

    override suspend fun toggleNotification(eventId: Long?, isNotificationEnabled: Boolean) {
        localEventRepository.toggleNotification(eventId, isNotificationEnabled)
    }
}