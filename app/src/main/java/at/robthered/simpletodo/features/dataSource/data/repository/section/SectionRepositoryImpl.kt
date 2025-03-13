package at.robthered.simpletodo.features.dataSource.data.repository.section

import at.robthered.simpletodo.features.dataSource.domain.repository.section.LocalSectionRepository
import at.robthered.simpletodo.features.dataSource.domain.local.model.SectionModel
import at.robthered.simpletodo.features.dataSource.domain.repository.section.SectionRepository
import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import kotlinx.coroutines.flow.Flow

class SectionRepositoryImpl(
    private val localSectionRepository: LocalSectionRepository,
) : SectionRepository {
    override suspend fun upsert(item: SectionModel): Result<Unit, Error> {
        return localSectionRepository.upsert(item)
    }

    override suspend fun upsert(items: List<SectionModel>): Result<Unit, Error> {
        return localSectionRepository.upsert(items)
    }

    override fun get(): Flow<List<SectionModel>> {
        return localSectionRepository.get()
    }

    override fun get(id: Long?): Flow<SectionModel?> {
        return localSectionRepository.get(id)
    }

    override suspend fun delete(id: Long?): Result<Unit, Error> {
        return localSectionRepository.delete(id)
    }

    override suspend fun delete(ids: List<Long>): Result<Unit, Error> {
        return localSectionRepository.delete(ids)
    }

    override suspend fun delete(): Result<Unit, Error> {
        return localSectionRepository.delete()
    }
}