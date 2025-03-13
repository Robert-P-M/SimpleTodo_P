package at.robthered.simpletodo.features.dataSource.data.use_case.link

import at.robthered.simpletodo.features.core.domain.error.DataError
import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.dataSource.domain.repository.link.LinkRepository
import at.robthered.simpletodo.features.dataSource.domain.use_case.link.RemoveLinkUseCase

class RemoveLinkUseCaseImpl(
    private val linkRepository: LinkRepository
) : RemoveLinkUseCase {
    override suspend fun invoke(linkUrl: String): Result<Unit, Error> {
        return  try {

            linkRepository.delete(linkUrl)
            Result.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    override suspend fun invoke(linkId: Long?): Result<Unit, Error> {
        return try {
            linkRepository.delete(linkId)
            Result.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(DataError.Local.UNKNOWN)
        }
    }
}