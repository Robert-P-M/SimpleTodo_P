package at.robthered.simpletodo.features.dataSource.data.repository.section

import at.robthered.simpletodo.features.dataSource.domain.repository.section.LocalSectionRepository
import at.robthered.simpletodo.features.dataSource.domain.local.model.SectionModel
import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.core.utils.safeCall
import at.robthered.simpletodo.features.dataSource.data.local.dao.section.SectionDao
import at.robthered.simpletodo.features.dataSource.data.local.mapper.toSectionEntity
import at.robthered.simpletodo.features.dataSource.data.local.mapper.toSectionModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalSectionRepositoryImpl(
    private val sectionDao: SectionDao,
) : LocalSectionRepository {
    override suspend fun upsert(item: SectionModel): Result<Unit, Error> {
        return safeCall {
            sectionDao.upsertSection(sectionEntity = item.toSectionEntity())
        }
    }

    override suspend fun upsert(items: List<SectionModel>): Result<Unit, Error> {
        return safeCall {
            sectionDao.upsertSections(sectionEntities = items.map { it.toSectionEntity() })
        }
    }

    override fun get(): Flow<List<SectionModel>> {
        return sectionDao.getAllSections().map { entities -> entities.map { it.toSectionModel() } }
    }

    override fun get(id: Long?): Flow<SectionModel?> {
        return sectionDao.getSection(id).map { it?.toSectionModel() }
    }

    override suspend fun delete(id: Long?): Result<Unit, Error> {
        return safeCall {
            sectionDao.deleteSection(id)
        }
    }

    override suspend fun delete(ids: List<Long>): Result<Unit, Error> {
        return safeCall {
            sectionDao.deleteMultipleSections(ids)
        }
    }

    override suspend fun delete(): Result<Unit, Error> {
        return safeCall {
            sectionDao.deleteAllSections()
        }
    }
}