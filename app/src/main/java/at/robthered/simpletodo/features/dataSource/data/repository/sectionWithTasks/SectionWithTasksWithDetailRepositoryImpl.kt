package at.robthered.simpletodo.features.dataSource.data.repository.sectionWithTasks

import at.robthered.simpletodo.features.dataSource.domain.local.relation.SectionWithTasksWithDetailsAndChildrenModel
import at.robthered.simpletodo.features.dataSource.domain.repository.sectionWithTasks.LocalSectionWithTasksWithDetailRepository
import at.robthered.simpletodo.features.dataSource.domain.repository.sectionWithTasks.SectionWithTasksWithDetailsRepository
import kotlinx.coroutines.flow.Flow

class SectionWithTasksWithDetailRepositoryImpl(
    private val localSectionWithTasksWithDetailsRepository: LocalSectionWithTasksWithDetailRepository
) : SectionWithTasksWithDetailsRepository {
    override fun getSectionsWithTasksWithDetails(depth: Int): Flow<List<SectionWithTasksWithDetailsAndChildrenModel>> {
        return localSectionWithTasksWithDetailsRepository.getSectionsWithTasksWithDetails(depth)
    }

    override fun getSectionWithTasksWithDetails(
        sectionId: Long?,
        depth: Int,
    ): Flow<SectionWithTasksWithDetailsAndChildrenModel?> {
        return localSectionWithTasksWithDetailsRepository.getSectionWithTasksWithDetails(sectionId, depth)
    }
}