package at.robthered.simpletodo.features.network.domain.use_case

import at.robthered.simpletodo.features.core.domain.Resource
import at.robthered.simpletodo.features.network.domain.model.SharedUrlModel
import kotlinx.coroutines.flow.Flow

interface GetSharedUrlDataUseCase {
    operator fun invoke(url: String): Flow<Resource<SharedUrlModel>>
}