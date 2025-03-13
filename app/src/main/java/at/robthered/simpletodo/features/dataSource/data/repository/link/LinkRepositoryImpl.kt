package at.robthered.simpletodo.features.dataSource.data.repository.link

import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.dataSource.domain.local.model.LinkModel
import at.robthered.simpletodo.features.dataSource.domain.repository.link.LinkRepository
import at.robthered.simpletodo.features.dataSource.domain.repository.link.LocalLinkRepository
import kotlinx.coroutines.flow.Flow

class LinkRepositoryImpl(
    private val localLinkRepository: LocalLinkRepository
) : LinkRepository {
    override suspend fun upsert(linkModel: LinkModel): Result<Long, Error> {
        return localLinkRepository.upsert(linkModel)
    }

    override fun getLinksOfTask(taskId: Long?): Flow<List<LinkModel>> {
        return localLinkRepository.getLinksOfTask(taskId)
    }

    override suspend fun delete(linkUrl: String) {
        return localLinkRepository.delete(linkUrl)
    }
    override suspend fun delete() {
        localLinkRepository.delete()
    }

    override suspend fun delete(linkId: Long?) {
        localLinkRepository.delete(linkId)
    }

    override suspend fun delete(ids: List<Long?>) {
        localLinkRepository.delete(ids)
    }

    override suspend fun deleteArchivedOfTask(taskId: Long?) {
        localLinkRepository.deleteArchivedOfTask(taskId)
    }
}