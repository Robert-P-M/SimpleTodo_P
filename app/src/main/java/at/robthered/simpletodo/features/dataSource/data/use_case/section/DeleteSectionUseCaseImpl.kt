package at.robthered.simpletodo.features.dataSource.data.use_case.section

import at.robthered.simpletodo.features.dataSource.domain.repository.section.SectionRepository
import at.robthered.simpletodo.features.dataSource.domain.use_case.section.DeleteSectionUseCase

class DeleteSectionUseCaseImpl(
    private val sectionRepository: SectionRepository
) : DeleteSectionUseCase {
    override suspend fun invoke(sectionId: Long?) {
        sectionRepository.delete(id = sectionId)
    }
}