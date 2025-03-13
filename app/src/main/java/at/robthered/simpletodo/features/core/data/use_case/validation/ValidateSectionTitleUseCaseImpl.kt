package at.robthered.simpletodo.features.core.data.use_case.validation

import at.robthered.simpletodo.features.core.domain.use_case.validation.ValidateSectionTitleUseCase
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.core.domain.error.ValidationError

class ValidateSectionTitleUseCaseImpl : ValidateSectionTitleUseCase {

    override operator fun invoke(value: String): Result<Unit, ValidationError.SectionTitle> {
        return when {
            value.isEmpty() -> Result.Error(ValidationError.SectionTitle.EMPTY)
            value.trim().length < ValidationError.SectionTitle.TOO_SHORT.minLength -> Result.Error(
                ValidationError.SectionTitle.TOO_SHORT
            )
            else -> Result.Success(Unit)
        }
    }

}