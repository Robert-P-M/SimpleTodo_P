package at.robthered.simpletodo.features.dataSource.data.local.dao.event

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import at.robthered.simpletodo.features.dataSource.data.local.entity.EventEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface EventDao {
    @Insert
    suspend fun insert(entity: EventEntity): Long

    @Upsert
    suspend fun upsert(entity: EventEntity): Long

    @Upsert
    suspend fun upsert(entities: List<EventEntity>): List<Long>

    @Query("SELECT * from event WHERE event_id =:eventId")
    fun get(eventId: Long?): Flow<EventEntity?>

    @Query("SELECT * FROM event WHERE task_id =:taskId ORDER BY created_at DESC")
    fun getEventsOfTask(taskId: Long?): Flow<List<EventEntity>>

    @Query("DELETE FROM event")
    suspend fun delete()

    @Query("DELETE FROM event WHERE event_id =:id")
    suspend fun delete(id: Long?)

    @Query("DELETE FROM event WHERE event_id IN (:ids)")
    suspend fun delete(ids: List<Long?>)

    @Query("DELETE FROM event WHERE task_id =:taskId")
    suspend fun deleteEventsOfTask(taskId: Long?)

    @Query("UPDATE event SET is_notification_enabled =:isNotificationEnabled WHERE event_id =:eventId")
    suspend fun toggleNotification(eventId: Long?, isNotificationEnabled: Boolean)

}