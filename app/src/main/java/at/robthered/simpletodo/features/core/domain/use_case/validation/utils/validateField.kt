package at.robthered.simpletodo.features.core.domain.use_case.validation.utils

import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.core.utils.handle
import at.robthered.simpletodo.features.core.domain.error.ValidationError

fun <T, U: ValidationError> validateField(
    fieldValue: T,
    validateUseCase: (T) -> Result<Unit, U>,
    updateState: (validationError: U?) -> Unit,
) {
    validateUseCase(fieldValue).handle { _, validationError ->
        updateState(validationError)
    }
}