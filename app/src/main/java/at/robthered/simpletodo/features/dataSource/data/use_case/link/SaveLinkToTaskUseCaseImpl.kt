package at.robthered.simpletodo.features.dataSource.data.use_case.link

import androidx.compose.runtime.mutableStateOf
import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.domain.error.ImageError
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.dataSource.domain.local.model.LinkModel
import at.robthered.simpletodo.features.dataSource.domain.repository.link.LinkRepository
import at.robthered.simpletodo.features.dataSource.domain.use_case.link.SaveLinkToTaskUseCase
import at.robthered.simpletodo.features.network.domain.model.SharedUrlModel
import at.robthered.simpletodo.features.shared.image.domain.use_case.TransformSharedUrlToLinkModelUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SaveLinkToTaskUseCaseImpl(
    private val linkRepository: LinkRepository,
    private val transformSharedUrlToLinkModelUseCase: TransformSharedUrlToLinkModelUseCase,
) : SaveLinkToTaskUseCase {
    override suspend operator fun invoke(sharedUrlModel: SharedUrlModel): Result<Long, Error> = withContext(Dispatchers.IO) {
        try {
            val linkModel = mutableStateOf<LinkModel?>(null)
            when (val model = transformSharedUrlToLinkModelUseCase(sharedUrlModel)) {
                    is Result.Error -> {
                        return@withContext Result.Error(ImageError.Storage.FILE_CREATION_FAILED)
                    }
                    is Result.Success -> {
                        linkModel.value = model.data
                    }
                }
            linkModel.value?.let {

                linkRepository.upsert(it)
            } ?: return@withContext Result.Error(ImageError.Storage.UNKNOWN)


        }catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}