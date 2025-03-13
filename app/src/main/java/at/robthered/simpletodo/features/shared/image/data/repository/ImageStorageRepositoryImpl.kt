package at.robthered.simpletodo.features.shared.image.data.repository

import android.content.Context
import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.domain.error.ImageError
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.shared.image.domain.repository.ImageStorageRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.io.File
import java.io.IOException

class ImageStorageRepositoryImpl(
    private val context: Context,
    private val httpClient: HttpClient,
) : ImageStorageRepository {
    override suspend fun saveImage(url: String, fileName: String): Flow<Result<String, Error>> = flow {
        try {
            val response: ByteArray = httpClient.get(url).body()
            val file = File(context.filesDir, fileName)
            file.outputStream().use {
                it.write(response)
            }
            emit(Result.Success(file.absolutePath))
        } catch (e: Exception) {
            throw e // Re-throw die Exception, damit catch sie verarbeiten kann
        }
    }.catch { e ->
        e.printStackTrace()
        when (e) {
            is IOException -> Result.Error(ImageError.Storage.WRITE_PERMISSION_DENIED)
            is SecurityException -> Result.Error(ImageError.Storage.FILE_CREATION_FAILED)
            else -> Result.Error(ImageError.Storage.UNKNOWN)
        }
    }
}