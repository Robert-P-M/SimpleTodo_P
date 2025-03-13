package at.robthered.simpletodo.features.dataSource.domain.repository.link

import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.dataSource.domain.local.model.LinkModel
import kotlinx.coroutines.flow.Flow

interface LinkRepository {
    suspend fun upsert(linkModel: LinkModel): Result<Long, Error>
    fun getLinksOfTask(taskId: Long?): Flow<List<LinkModel>>
    suspend fun delete()
    suspend fun delete(linkUrl: String)
    suspend fun delete(linkId: Long?)
    suspend fun delete(ids: List<Long?>)
    suspend fun deleteArchivedOfTask(taskId: Long?)
}