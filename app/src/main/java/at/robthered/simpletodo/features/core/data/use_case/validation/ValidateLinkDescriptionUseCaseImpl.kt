package at.robthered.simpletodo.features.core.data.use_case.validation

import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.core.domain.error.ValidationError
import at.robthered.simpletodo.features.core.domain.use_case.validation.ValidateLinkDescriptionUseCase

class ValidateLinkDescriptionUseCaseImpl : ValidateLinkDescriptionUseCase {
    override fun invoke(value: String?): Result<Unit, ValidationError> {
        return when {
            !value.isNullOrEmpty() && value.trim().length < ValidationError.LinkDescription.TOO_SHORT.minLength -> Result.Error(
                ValidationError.LinkDescription.TOO_SHORT
            )
            value == null -> Result.Success(Unit)
            else -> Result.Success(Unit)
        }
    }
}