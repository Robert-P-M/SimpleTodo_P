package at.robthered.simpletodo.features.core.domain.dataStore

import kotlinx.coroutines.flow.Flow

interface AppDataStore {
    suspend fun saveExpandedItems(expandedItems: List<Long?>)
    fun getExpandedItems(): Flow<List<Long?>>
}