package at.robthered.simpletodo.features.core.data.use_case.validation

import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.core.domain.error.ValidationError
import at.robthered.simpletodo.features.core.domain.use_case.validation.ValidateLinkTitleUseCase

class ValidateLinkTitleUseCaseImpl : ValidateLinkTitleUseCase {
    override fun invoke(value: String): Result<Unit, ValidationError> {
        return when {
            value.isEmpty() -> Result.Error(ValidationError.LinkTitle.EMPTY)
            value.trim().length < ValidationError.LinkTitle.EMPTY.minLength -> Result.Error(
                ValidationError.LinkTitle.TOO_SHORT)
            else -> Result.Success(Unit)
        }
    }
}