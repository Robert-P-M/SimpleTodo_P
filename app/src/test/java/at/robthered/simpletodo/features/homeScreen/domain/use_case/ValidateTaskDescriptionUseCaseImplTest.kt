package at.robthered.simpletodo.features.homeScreen.domain.use_case

import at.robthered.simpletodo.features.core.data.use_case.validation.ValidateTaskDescriptionUseCaseImpl
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.core.domain.error.ValidationError
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test


class ValidateTaskDescriptionUseCaseImplTest {


    private val useCase = ValidateTaskDescriptionUseCaseImpl()

    @Test
    fun `returns success when description is null`() {
        val result = useCase(null)

        assertTrue(result is Result.Success)
    }

    @Test
    fun `returns error when description is empty`() {
        val result = useCase(" ")
        assertTrue(result is Result.Error)
    }

    @Test
    fun `returns error when description is too short`() {
        val result = useCase("ab")
        assertTrue(result is Result.Error)
        assertEquals(
            ValidationError.TaskDescription.TOO_SHORT,
            (result as Result.Error).error
        )
    }

    @Test
    fun `returns success when description is valid`() {
        val result = useCase("This is a valid description")
        assertTrue(result is Result.Success)
    }
}