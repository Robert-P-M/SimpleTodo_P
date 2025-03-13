package at.robthered.simpletodo.features.dataSource.data.repository.link

import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.core.utils.safeCall
import at.robthered.simpletodo.features.dataSource.data.local.dao.link.LinkDao
import at.robthered.simpletodo.features.dataSource.data.local.mapper.toLinkEntity
import at.robthered.simpletodo.features.dataSource.data.local.mapper.toLinkModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.LinkModel
import at.robthered.simpletodo.features.dataSource.domain.repository.link.LocalLinkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalLinkRepositoryImpl(
    private val linkDao: LinkDao
) : LocalLinkRepository {
    override suspend fun upsert(linkModel: LinkModel): Result<Long, Error> {
        return safeCall {
            linkDao.upsert(linkEntity = linkModel.toLinkEntity())
        }
    }

    override fun getLinksOfTask(taskId: Long?): Flow<List<LinkModel>> {
        return linkDao.getLinksOfTask(taskId)
            .map { entities -> entities.map { it.toLinkModel() } }
    }

    override suspend fun delete(linkUrl: String) {
        linkDao.delete(linkUrl)
    }
    override suspend fun delete() {
        linkDao.delete()
    }

    override suspend fun delete(linkId: Long?) {
        linkDao.delete(linkId)
    }

    override suspend fun delete(ids: List<Long?>) {
        linkDao.delete(ids)
    }

    override suspend fun deleteArchivedOfTask(taskId: Long?) {
        linkDao.deleteArchivedOfTask(taskId)
    }
}