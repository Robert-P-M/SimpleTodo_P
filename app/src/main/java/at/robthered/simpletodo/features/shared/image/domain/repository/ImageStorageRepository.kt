package at.robthered.simpletodo.features.shared.image.domain.repository

import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import kotlinx.coroutines.flow.Flow

interface ImageStorageRepository {
    suspend fun saveImage(url: String, fileName: String): Flow<Result<String, Error>>
}