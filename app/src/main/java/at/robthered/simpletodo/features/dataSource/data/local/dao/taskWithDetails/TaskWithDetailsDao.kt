package at.robthered.simpletodo.features.dataSource.data.local.dao.taskWithDetails

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import at.robthered.simpletodo.features.dataSource.data.local.relation.TaskWithDetailsRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskWithDetailsDao {
    @Transaction
    @Query("SELECT * FROM task WHERE section_id IS NULL AND parent_task_id IS NULL ORDER BY created_at ASC")
    fun get(): Flow<List<TaskWithDetailsRelation>>
    @Transaction
    @Query("SELECT * FROM task WHERE task_id =:taskId")
    fun get(taskId: Long?): Flow<TaskWithDetailsRelation?>
    @Transaction
    @Query("SELECT * FROM task WHERE parent_task_id =:parentTaskId ORDER BY created_at ASC")
    fun getTasksOfParentWithDetails(parentTaskId: Long?): Flow<List<TaskWithDetailsRelation>>
}