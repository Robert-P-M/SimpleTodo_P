package at.robthered.simpletodo.features.network.data.repository

import at.robthered.simpletodo.features.core.domain.error.DataError
import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.network.data.dto.SharedUrlDto
import at.robthered.simpletodo.features.network.data.mapper.toModel
import at.robthered.simpletodo.features.network.domain.model.SharedUrlModel
import at.robthered.simpletodo.features.network.domain.repository.ShareUrlRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import org.apache.commons.text.StringEscapeUtils
import java.io.IOException


class ShareUrlRepositoryImpl(
    private val client: HttpClient
) : ShareUrlRepository {
    override suspend fun getSharedUrlData(url: String): Result<SharedUrlModel, Error> {

        return try {

            val response = client.get(url)

            when (val result = responseToResult<String>(response)) {
                is Result.Error -> Result.Error(result.error)
                is Result.Success -> Result.Success(extractMetaData(result.data, url).toModel())

            }
        } catch (e: Exception) {
            e.printStackTrace()
            when(e){
                is HttpRequestTimeoutException -> Result.Error(DataError.RemoteError.REQUEST_TIMEOUT)
                is IOException -> Result.Error(DataError.RemoteError.NO_INTERNET)
                else -> Result.Error(DataError.RemoteError.UNKNOWN)
            }

        }
    }

    private suspend inline fun <reified T> responseToResult(response: HttpResponse): Result<T, DataError.RemoteError> {
        return when(response.status.value) {
            in 200..299 -> Result.Success(response.body<T>())
            401 -> Result.Error(DataError.RemoteError.UNAUTHORIZED)
            408 -> Result.Error(DataError.RemoteError.REQUEST_TIMEOUT)
            409 -> Result.Error(DataError.RemoteError.CONFLICT)
            413 -> Result.Error(DataError.RemoteError.PAYLOAD_TOO_LARGE)
            429 -> Result.Error(DataError.RemoteError.TOO_MANY_REQUESTS)
            in 500..599 -> Result.Error(DataError.RemoteError.SERVER_ERROR)
            else -> Result.Error(DataError.RemoteError.UNKNOWN)
        }
    }

    private fun extractMetaData(html: String, url: String): SharedUrlDto {
        return SharedUrlDto(
            title = unEscapeHtml4(extractMetaTag(html, "og:title") ?: extractMetaTag(html, "title") ?: "- NA -" ) ,
            description = unEscapeHtml4(extractMetaTag(html, "og:description") ?: extractMetaTag(html, "description") ?: "- NA -"),
            link = url,
            imageUrl = extractMetaTag(html, "og:image") ?: extractMetaTag(html, "image") ,
        )
    }

    private fun extractMetaTag(html: String, property: String): String? {
        val regex =
            """<meta\s+[^>]*?(?:property|name)=["']$property["'][^>]*?content=["']([^"']+)["'][^>]*?>""".toRegex(
                RegexOption.IGNORE_CASE
            )
        return regex.find(html)?.groupValues?.get(1)
    }

    private fun unEscapeHtml4(rawValue: String): String {
        return StringEscapeUtils.unescapeHtml4(rawValue)
    }
}