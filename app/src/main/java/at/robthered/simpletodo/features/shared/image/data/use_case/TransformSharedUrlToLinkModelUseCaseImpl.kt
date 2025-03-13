package at.robthered.simpletodo.features.shared.image.data.use_case

import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.dataSource.domain.local.model.LinkModel
import at.robthered.simpletodo.features.network.domain.model.SharedUrlModel
import at.robthered.simpletodo.features.shared.image.domain.repository.ImageStorageRepository
import at.robthered.simpletodo.features.shared.image.domain.use_case.TransformSharedUrlToLinkModelUseCase
import kotlinx.coroutines.flow.first

class TransformSharedUrlToLinkModelUseCaseImpl(
    private val imageStorageRepository: ImageStorageRepository
): TransformSharedUrlToLinkModelUseCase {
    override suspend operator fun invoke(sharedUrlModel: SharedUrlModel): Result<LinkModel, Error> {
        val imageFilePathResult = sharedUrlModel.imageUrl?.let { url ->
            val fileName = "img_${System.currentTimeMillis()}.jpg"
            imageStorageRepository.saveImage(url, fileName).first { it is Result.Success || it is Result.Error }
        } ?: Result.Success(null)

        return when(imageFilePathResult) {
            is Result.Error -> {
                Result.Error(imageFilePathResult.error)
            }
            is Result.Success -> LinkModel(
                title = sharedUrlModel.title,
                description = sharedUrlModel.description,
                linkUrl = sharedUrlModel.link,
                imageFilePath = imageFilePathResult.data,
                createdAt = sharedUrlModel.createdAt,
                updatedAt = sharedUrlModel.updatedAt,
                taskId = sharedUrlModel.taskId
            ).let { Result.Success(it) }
        }
    }
}