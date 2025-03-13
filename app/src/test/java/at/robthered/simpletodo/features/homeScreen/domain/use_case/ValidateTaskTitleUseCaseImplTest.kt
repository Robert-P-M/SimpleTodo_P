package at.robthered.simpletodo.features.homeScreen.domain.use_case

import at.robthered.simpletodo.features.core.data.use_case.validation.ValidateTaskTitleUseCaseImpl
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.core.domain.error.ValidationError
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ValidateTaskTitleUseCaseImplTest {

    private val useCase = ValidateTaskTitleUseCaseImpl()

    @Test
    fun `returns error when title is empty`() {
        val result = useCase("")
        assertTrue(result is Result.Error)
        assertEquals(ValidationError.TaskTitle.EMPTY, result.error)
    }

    @Test
    fun `returns error when title is too short`() {
        val result = useCase("s")
        assertTrue(result is Result.Error)
        assertEquals(ValidationError.TaskTitle.TOO_SHORT, result.error)
    }

    @Test
    fun `returns success when title is valid`() {
        val result = useCase("Valid Title")
        assertTrue(result is Result.Success)
    }
}