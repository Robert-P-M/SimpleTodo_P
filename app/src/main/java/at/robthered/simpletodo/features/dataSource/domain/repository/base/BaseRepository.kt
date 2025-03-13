package at.robthered.simpletodo.features.dataSource.domain.repository.base

import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import kotlinx.coroutines.flow.Flow

interface BaseRepository<T> {
    suspend fun upsert(item: T): Result<Unit, Error>
    suspend fun upsert(items: List<T>): Result<Unit, Error>
    fun get(): Flow<List<T>>
    fun get(id: Long?): Flow<T?>
    suspend fun delete(id: Long?): Result<Unit, Error>
    suspend fun delete(ids: List<Long>): Result<Unit, Error>
    suspend fun delete(): Result<Unit, Error>
}