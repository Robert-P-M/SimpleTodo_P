package at.robthered.simpletodo.features.dataSource.data.use_case.section

import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.dataSource.domain.local.model.SectionModel
import at.robthered.simpletodo.features.dataSource.domain.repository.section.SectionRepository
import at.robthered.simpletodo.features.dataSource.domain.use_case.section.SaveSectionUseCase

class SaveSectionUseCaseImpl(
    private val sectionRepository: SectionRepository
) : SaveSectionUseCase {
    override suspend fun invoke(sectionModel: SectionModel): Result<Unit, Error> {
        return sectionRepository.upsert(item = sectionModel)
    }
}