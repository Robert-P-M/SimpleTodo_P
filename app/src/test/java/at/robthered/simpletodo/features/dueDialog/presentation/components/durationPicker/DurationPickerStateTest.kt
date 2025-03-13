package at.robthered.simpletodo.features.dueDialog.presentation.components.durationPicker

import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.mockito.kotlin.any

inline fun <reified T> Any.invokePrivateFunction(name: String, vararg args: Any?) :T {
    val method = this::class.java.getDeclaredMethod(name, *args.map { it?.javaClass }.toTypedArray())
    method.isAccessible = true
    return method.invoke(this, *args) as T
}

class DurationPickerStateTest {

    @Test
    fun `initializeValues sets correct initial hour and minute`() {
        val state = DurationPickerState(125, {}, {})
        assertEquals(2, state.currentHour)
        assertEquals(5, state.currentMinute)
    }
    @Test
    fun `initializeValues sets zero values when initialMinutes is null`() {
        val state = DurationPickerState(null, {}, {})
        assertEquals(0, state.currentHour)
        assertEquals(0, state.currentMinute)
    }

    @Test
    fun `setHour respects valid range`() {
        val state = DurationPickerState(null, {}, {})
        state.setHour(25)
        assertEquals(23, state.currentHour)
        state.setHour(-1)
        assertEquals(0, state.currentHour)
    }


    @Test
    fun `newMinutes returns correct value`() {

        val state = spy(DurationPickerState(null, {}, {}))
        state.currentHour = 1
        state.currentMinute = 30

        assertEquals(90, state.invokePrivateFunction<Int>("newMinutes"))
        /**
         * val method = state.javaClass.getDeclaredMethod("newMinute", Int::class.java)
         */
    }

    @Test
    fun `save calls onSave with correct value and hides dialog`() {
        val onSave = mock<(Int) -> Unit>()
        val state = DurationPickerState(30, onSave, {})
        state.save()
        verify(onSave).invoke(30)
        assertFalse(state.isVisible)
    }

    @Test
    fun `save does not call onSave when newMinutes is zero`() {
        val onSave = mock<(Int) -> Unit>()
        val state = DurationPickerState(0, onSave, {})
        state.save()
        verify(onSave, never()).invoke(any())
    }

    @Test
    fun `reset reinitializes values and calls onClear`() {
        val onClear = mock<() -> Unit>()
        val state = DurationPickerState(90, {}, onClear)
        state.currentHour = 5
        state.reset()
        assertEquals(1, state.currentHour)
        assertEquals(30, state.currentMinute)
        verify(onClear).invoke()
        assertFalse(state.isVisible)
    }

    @Test
    fun `onShow sets isVisible to true`() {
        val state = DurationPickerState(null, {}, {})
        assertFalse(state.isVisible)
        state.onShow()
        assertTrue(state.isVisible)
    }
    @Test
    fun `setHour and setMinute correctly update newMinutes`() {
        val state = DurationPickerState(null, {}, {})
        state.setHour(2)
        state.setMinute(30)
        assertEquals(150, state.invokePrivateFunction<Int>("newMinutes"))
    }
    @Test
    fun `initializeValues handles large initialMinutes correctly`() {
        val state = DurationPickerState(1440, {}, {}) // 24 hours
        assertEquals(24, state.currentHour)
        assertEquals(0, state.currentMinute)
    }
    @Test
    fun `onHide sets isVisible to false`() {
        val state = DurationPickerState(null, {}, {})
        state.onShow()
        state.onHide()
        assertFalse(state.isVisible)
    }

    @Test
    fun `setMinute respects valid range`() {
        val state = DurationPickerState(null, {}, {})
        state.setMinute(61)
        assertEquals(59, state.currentMinute)
        state.setMinute(-5)
        assertEquals(0, state.currentMinute)
    }
}