package at.robthered.simpletodo.features.dataSource.data.local.dao.eventToTask

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import at.robthered.simpletodo.features.dataSource.data.local.relation.EventToTaskRelation
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock

@Dao
interface EventToTaskDao {
    @Transaction
    @Query("SELECT * FROM event WHERE event_id IN (SELECT current_event_id FROM task WHERE current_event_id IS NOT NULL) AND is_active = 1 AND is_notification_enabled =:isNotificationEnabled")
    fun get(isNotificationEnabled: Boolean = false): Flow<List<EventToTaskRelation>>
}