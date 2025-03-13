package at.robthered.simpletodo.features.dataSource.domain.use_case.archived

import at.robthered.simpletodo.features.dataSource.domain.local.model.ArchivedModel

interface ArchiveTaskUseCase {
    suspend operator fun invoke(archivedModel: ArchivedModel)
}