package at.robthered.simpletodo.features.shared.image.domain.use_case

import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import kotlinx.coroutines.flow.Flow

interface SaveImageUseCase {
    suspend operator fun invoke(url: String, fileName: String): Flow<Result<String, Error>>
}