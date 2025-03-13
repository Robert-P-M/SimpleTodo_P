package at.robthered.simpletodo.features.dataSource.data.local.dao.priority

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import at.robthered.simpletodo.features.dataSource.data.local.entity.PriorityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PriorityDao {

    @Insert
    suspend fun insertPriority(priorityEntity: PriorityEntity): Long
    @Upsert
    suspend fun upsertPriority(priorityEntity: PriorityEntity): Long

    @Query("SELECT * FROM priority WHERE task_id =:taskId")
    fun getPrioriesForTask(taskId: Long?): Flow<List<PriorityEntity>>

    @Query("DELETE FROM priority")
    suspend fun delete()

    @Query("DELETE FROM priority WHERE priority_id =:id")
    suspend fun delete(id: Long?)

    @Query("DELETE FROM priority WHERE priority_id IN (:ids)")
    suspend fun delete(ids: List<Long?>)

    @Query("DELETE FROM priority WHERE task_id =:taskId")
    suspend fun deletePrioritiesOfTask(taskId: Long?)
}