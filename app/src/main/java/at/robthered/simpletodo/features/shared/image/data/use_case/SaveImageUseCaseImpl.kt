package at.robthered.simpletodo.features.shared.image.data.use_case

import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.shared.image.domain.repository.ImageStorageRepository
import at.robthered.simpletodo.features.shared.image.domain.use_case.SaveImageUseCase
import kotlinx.coroutines.flow.Flow

class SaveImageUseCaseImpl(
    private val imageStorageRepository: ImageStorageRepository
) : SaveImageUseCase {
    override suspend fun invoke(url: String, fileName: String): Flow<Result<String, Error>> {
        return imageStorageRepository.saveImage(url, fileName)
    }
}