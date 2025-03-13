package at.robthered.simpletodo.features.network.domain.repository

import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.network.domain.model.SharedUrlModel

interface ShareUrlRepository {
    suspend fun getSharedUrlData(url: String): Result<SharedUrlModel, Error>
}