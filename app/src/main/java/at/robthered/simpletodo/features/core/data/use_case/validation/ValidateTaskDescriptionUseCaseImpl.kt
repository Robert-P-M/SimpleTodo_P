package at.robthered.simpletodo.features.core.data.use_case.validation

import at.robthered.simpletodo.features.core.domain.use_case.validation.ValidateTaskDescriptionUseCase
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.core.domain.error.ValidationError

class ValidateTaskDescriptionUseCaseImpl : ValidateTaskDescriptionUseCase {

    override operator fun invoke(value: String?): Result<Unit, ValidationError.TaskDescription> {
        return when {
            !value.isNullOrEmpty() && value.trim().length < ValidationError.TaskDescription.TOO_SHORT.minLength -> Result.Error(
                ValidationError.TaskDescription.TOO_SHORT)
            value == null -> Result.Success(Unit)
            else -> Result.Success(Unit)
        }
    }


}