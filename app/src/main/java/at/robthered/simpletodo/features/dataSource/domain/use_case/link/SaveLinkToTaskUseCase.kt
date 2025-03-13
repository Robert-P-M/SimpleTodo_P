package at.robthered.simpletodo.features.dataSource.domain.use_case.link

import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.network.domain.model.SharedUrlModel

interface SaveLinkToTaskUseCase {
    suspend operator fun invoke(sharedUrlModel: SharedUrlModel): Result<Long, Error>
}