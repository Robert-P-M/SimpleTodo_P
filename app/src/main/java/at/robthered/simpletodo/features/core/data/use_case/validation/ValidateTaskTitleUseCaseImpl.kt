package at.robthered.simpletodo.features.core.data.use_case.validation

import at.robthered.simpletodo.features.core.domain.use_case.validation.ValidateTaskTitleUseCase
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.core.domain.error.ValidationError

class ValidateTaskTitleUseCaseImpl : ValidateTaskTitleUseCase {

    override operator fun invoke(value: String): Result<Unit, ValidationError.TaskTitle> {
        return when {
            value.isEmpty() -> Result.Error(ValidationError.TaskTitle.EMPTY)
            value.trim().length < ValidationError.TaskTitle.EMPTY.minLength -> Result.Error(
                ValidationError.TaskTitle.TOO_SHORT)
            else -> Result.Success(Unit)
        }
    }
}