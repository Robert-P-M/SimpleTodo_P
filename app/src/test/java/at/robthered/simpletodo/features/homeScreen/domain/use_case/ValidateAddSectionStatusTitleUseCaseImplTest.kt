package at.robthered.simpletodo.features.homeScreen.domain.use_case

import at.robthered.simpletodo.features.core.data.use_case.validation.ValidateSectionTitleUseCaseImpl
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.core.domain.error.ValidationError
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidateAddSectionStatusTitleUseCaseImplTest {

    private val useCase = ValidateSectionTitleUseCaseImpl()

    @Test
    fun `returns error when section title is empty`() {
        val result = useCase("")
        assertTrue(result is Result.Error)
        assertEquals(ValidationError.SectionTitle.EMPTY, (result as Result.Error).error)
    }

    @Test
    fun `returns error when section title is too short`() {
        val result = useCase("ab")
        assertTrue(result is Result.Error)
        assertEquals(ValidationError.SectionTitle.TOO_SHORT, (result as Result.Error).error)
    }

    @Test
    fun `returns success when section title is valid`() {
        val result = useCase("My Section")
        assertTrue(result is Result.Success)
    }
}