package at.robthered.simpletodo.features.core.domain.use_case.validation

import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.core.domain.error.ValidationError

interface ValidateTaskTitleUseCase {
    operator fun invoke(value: String): Result<Unit, ValidationError>
}