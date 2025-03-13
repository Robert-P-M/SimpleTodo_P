package at.robthered.simpletodo.features.dataSource.domain.repository.event

import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.dataSource.data.local.entity.EventEntity
import at.robthered.simpletodo.features.dataSource.domain.local.model.EventModel
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    suspend fun upsert(eventModel: EventModel): Result<Long, Error>
    fun get(eventId: Long?): Flow<EventEntity?>
    fun getEventsForTask(taskId: Long?): Flow<List<EventModel>>
    suspend fun delete()
    suspend fun delete(id: Long?)
    suspend fun delete(ids: List<Long?>)
    suspend fun deleteEventsOfTask(taskId: Long?)
    suspend fun toggleNotification(eventId: Long?, isNotificationEnabled: Boolean)
}