package at.robthered.simpletodo.features.dataSource.data.local.dao.completed

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import at.robthered.simpletodo.features.dataSource.data.local.entity.CompletedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CompletedDao {
    @Upsert
    suspend fun upsert(completedEntity: CompletedEntity)

    @Query("SELECT * FROM completed WHERE task_id =:taskId")
    fun getCompletionsForTask(taskId: Long?): Flow<List<CompletedEntity>>

    @Query("DELETE FROM completed")
    suspend fun delete()

    @Query("DELETE FROM completed WHERE completed_id =:id")
    suspend fun delete(id: Long?)

    @Query("DELETE FROM completed WHERE completed_id IN (:ids)")
    suspend fun delete(ids: List<Long?>)

    @Query("DELETE FROM completed WHERE task_id =:taskId")
    suspend fun deleteCompletedOfTask(taskId: Long?)
}