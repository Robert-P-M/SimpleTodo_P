package at.robthered.simpletodo.features.core.data.use_case.validation

import android.util.Patterns
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.core.domain.error.ValidationError
import at.robthered.simpletodo.features.core.domain.use_case.validation.ValidateLinkUrlUseCase
import java.net.URL

class ValidateLinkUrlUseCaseImpl: ValidateLinkUrlUseCase {
    override operator fun invoke(value: String): Result<String, ValidationError.LinkUrl> {

        val normalizedUrl = normalizeUrl(value)

        return when {
            normalizedUrl.isBlank() -> Result.Error(ValidationError.LinkUrl.EMPTY)
            !isValidUrlFormat(normalizedUrl) -> Result.Error(ValidationError.LinkUrl.INVALID)
            isMalFormed(normalizedUrl) -> Result.Error(ValidationError.LinkUrl.MALFORMED)
            normalizedUrl.length > MAX_URL_LENGTH -> Result.Error(ValidationError.LinkUrl.TOO_LONG)
            else -> Result.Success(normalizedUrl)
        }
    }


    private fun isValidUrlFormat(url: String): Boolean {
        return Patterns.WEB_URL.matcher(url).matches()
    }

    private fun isMalFormed(url: String): Boolean {
        return try {
            URL(url)
            false
        } catch (e: Exception) {
            true
        }
    }

    private fun normalizeUrl(url: String): String {
        return when {
            url.startsWith("http://", ignoreCase = true) -> url
            url.startsWith("https://", ignoreCase = true) -> url
            else -> "https://$url"
        }.trim()
    }

    companion object {
        private const val MAX_URL_LENGTH = 2048
    }
}