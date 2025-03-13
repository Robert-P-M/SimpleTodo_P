package at.robthered.simpletodo.features.dataSource.data.local.dao.section

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import at.robthered.simpletodo.features.dataSource.data.local.entity.SectionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SectionDao {
    @Upsert
    suspend fun upsertSection(sectionEntity: SectionEntity)

    @Upsert
    suspend fun upsertSections(sectionEntities: List<SectionEntity>)

    @Query("SELECT * FROM section ORDER BY created_at DESC")
    fun getAllSections(): Flow<List<SectionEntity>>

    @Query("SELECT * FROM section WHERE section_id =:sectionId")
    fun getSection(sectionId: Long?): Flow<SectionEntity?>

    @Query("DELETE FROM section WHERE section_id =:sectionId")
    suspend fun deleteSection(sectionId: Long?)

    @Query("DELETE FROM section WHERE section_id IN (:sectionIds)")
    suspend fun deleteMultipleSections(sectionIds: List<Long>)

    @Query("DELETE FROM section")
    suspend fun deleteAllSections()
}