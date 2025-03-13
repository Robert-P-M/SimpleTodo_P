package at.robthered.simpletodo.features.dataSource.domain.use_case.link

import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result

interface RemoveLinkUseCase {
    suspend operator fun invoke(linkUrl: String): Result<Unit, Error>
    suspend operator fun invoke(linkId: Long?): Result<Unit, Error>
}