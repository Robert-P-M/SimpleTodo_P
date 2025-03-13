package at.robthered.simpletodo.features.dataSource.domain.use_case.section

import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.dataSource.domain.local.model.SectionModel

interface SaveSectionUseCase {
    suspend operator fun invoke(sectionModel: SectionModel): Result<Unit, Error>

}