package at.robthered.simpletodo.features.dataSource.data.local.dao.link

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import at.robthered.simpletodo.features.dataSource.data.local.entity.LinkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LinkDao {

    @Insert
    suspend fun insert(linkEntity: LinkEntity): Long
    @Upsert
    suspend fun upsert(linkEntity: LinkEntity): Long

    @Query("SELECT * FROM link WHERE task_id =:taskId ORDER BY created_at DESC")
    fun getLinksOfTask(taskId: Long?): Flow<List<LinkEntity>>

    @Query("DELETE FROM link")
    suspend fun delete()

    @Query("DELETE FROM link WHERE link_url =:linkUrl")
    suspend fun delete(linkUrl: String)

    @Query("DELETE FROM link WHERE link_id =:linkId")
    suspend fun delete(linkId: Long?)

    @Query("DELETE FROM link WHERE link_id IN(:ids)")
    suspend fun delete(ids: List<Long?>)

    @Query("DELETE FROM link WHERE task_id =:taskId")
    suspend fun deleteArchivedOfTask(taskId: Long?)

}