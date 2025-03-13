package at.robthered.simpletodo.features.dataSource.data.local.dao.archived

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import at.robthered.simpletodo.features.dataSource.data.local.entity.ArchivedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArchivedDao {
    @Upsert
    suspend fun upsert(archivedEntity: ArchivedEntity)

    @Query("SELECT * FROM archived WHERE task_id =:taskId ORDER BY created_at DESC")
    fun getArchivedOfTask(taskId: Long?): Flow<List<ArchivedEntity>>

    @Query("DELETE FROM archived")
    suspend fun delete()

    @Query("DELETE FROM archived WHERE archived_id =:id")
    suspend fun delete(id: Long?)

    @Query("DELETE FROM archived WHERE archived_id IN (:ids)")
    suspend fun delete(ids: List<Long?>)

    @Query("DELETE FROM archived WHERE task_id =:taskId")
    suspend fun deleteArchivedOfTask(taskId: Long?)
}