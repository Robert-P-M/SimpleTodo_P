package at.robthered.simpletodo.features.dataSource.data.use_case.archived

import at.robthered.simpletodo.features.dataSource.domain.local.model.ArchivedModel
import at.robthered.simpletodo.features.dataSource.domain.repository.archived.ArchivedRepository
import at.robthered.simpletodo.features.dataSource.domain.use_case.archived.ArchiveTaskUseCase

class ArchiveTaskUseCaseImpl(
    private val archivedRepository: ArchivedRepository
) : ArchiveTaskUseCase {
    override suspend fun invoke(archivedModel: ArchivedModel) {
        archivedRepository.upsert(archivedModel)
    }
}