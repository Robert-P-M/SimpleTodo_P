package at.robthered.simpletodo.features.dataSource.data.local.dao.sectionWithTasks

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import at.robthered.simpletodo.features.dataSource.data.local.relation.SectionWithTasksWithDetailsRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface SectionWithTasksWithDetailsDao {
    @Transaction
    @Query("SELECT * FROM section")
    fun get(): Flow<List<SectionWithTasksWithDetailsRelation>>

    @Transaction
    @Query("SELECT * FROM section WHERE section_id =:sectionId")
    fun get(sectionId: Long?): Flow<SectionWithTasksWithDetailsRelation?>

}