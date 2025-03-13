package at.robthered.simpletodo.features.dataSource.data.local.dao.task

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import at.robthered.simpletodo.features.dataSource.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert
    suspend fun insert(taskEntity: TaskEntity): Long

    @Upsert
    suspend fun upsertTask(taskEntity: TaskEntity): Long

    @Upsert
    fun upsertTasks(taskEntities: List<TaskEntity>)

    @Query("SELECT * FROM task WHERE section_id IS NULL ORDER BY created_at DESC")
    fun getAllTasksWithoutSection(): Flow<List<TaskEntity>>


    @Query("SELECT * FROM task ORDER BY created_at DESC")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM task WHERE parent_task_id =:parentTaskId")
    fun getChildTasks(parentTaskId: Long?): Flow<List<TaskEntity>>

    @Query("SELECT * FROM task WHERE task_id =:taskId")
    fun getTask(taskId: Long?): Flow<TaskEntity?>

    @Query("DELETE FROM task WHERE task_id =:taskId")
    suspend fun deleteTask(taskId: Long?)

    @Query("DELETE FROM task WHERE task_id IN (:taskIds)")
    suspend fun deleteMultipleTasks(taskIds: List<Long>)

    @Query("SELECT * FROM task WHERE current_event_id =:eventId")
    fun getTaskOfEvent(eventId: Long?): Flow<TaskEntity?>

    @Query("DELETE FROM task")
    suspend fun deleteAllTasks()
}