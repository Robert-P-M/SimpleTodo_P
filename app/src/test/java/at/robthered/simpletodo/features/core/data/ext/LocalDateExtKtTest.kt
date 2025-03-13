package at.robthered.simpletodo.features.core.data.ext

import at.robthered.simpletodo.features.core.utils.ext.isoWeekNumber
import kotlinx.datetime.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Test

class LocalDateExtKtTest {
    @Test
    fun `isoWeek of 11 February 2025 should be 7`() {
        val isoWeekNumber = LocalDate(year = 2025, monthNumber = 2, dayOfMonth = 11).isoWeekNumber()
        assertEquals(isoWeekNumber, 7)
    }
}