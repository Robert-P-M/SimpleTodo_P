package at.robthered.simpletodo.features.shared.image.domain.use_case

import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.dataSource.domain.local.model.LinkModel
import at.robthered.simpletodo.features.network.domain.model.SharedUrlModel

interface TransformSharedUrlToLinkModelUseCase {
    suspend operator fun invoke(sharedUrlModel: SharedUrlModel) : Result<LinkModel, Error>
}