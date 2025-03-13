package at.robthered.simpletodo.features.dataSource.domain.repository.sectionWithTasks

import at.robthered.simpletodo.features.dataSource.domain.local.relation.SectionWithTasksWithDetailsAndChildrenModel
import kotlinx.coroutines.flow.Flow

interface LocalSectionWithTasksWithDetailRepository {
    fun getSectionsWithTasksWithDetails(
        depth: Int = 5
    ): Flow<List<SectionWithTasksWithDetailsAndChildrenModel>>
    fun getSectionWithTasksWithDetails(
        sectionId: Long?,
        depth: Int = 5
    ): Flow<SectionWithTasksWithDetailsAndChildrenModel?>
}