package at.robthered.simpletodo.features.network.data.use_case

import at.robthered.simpletodo.features.core.domain.Resource
import at.robthered.simpletodo.features.core.domain.use_case.validation.ValidateLinkUrlUseCase
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.core.utils.onError
import at.robthered.simpletodo.features.core.utils.onSuccess
import at.robthered.simpletodo.features.core.presentation.error.toUiText
import at.robthered.simpletodo.features.network.domain.model.SharedUrlModel
import at.robthered.simpletodo.features.network.domain.repository.ShareUrlRepository
import at.robthered.simpletodo.features.network.domain.use_case.GetSharedUrlDataUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSharedUrlDataUseCaseImpl(
    private val shareUrlRepository: ShareUrlRepository,
    private val validateSharedUrlUseCase: ValidateLinkUrlUseCase
) : GetSharedUrlDataUseCase {
    override operator fun invoke(url: String): Flow<Resource<SharedUrlModel>> = flow {
        emit(Resource.Loading())
        when(val validationResult = validateSharedUrlUseCase(url)){
            is Result.Error -> {
                emit(Resource.Error(error = validationResult.error.toUiText()))
            }
            is Result.Success -> {
                shareUrlRepository.getSharedUrlData(validationResult.data)
                    .onSuccess { dto: SharedUrlModel ->
                        emit(Resource.Success(data = dto))
                    }
                    .onError {
                        emit(Resource.Error(error = it.toUiText()))
                    }
            }
        }

    }
}