package at.robthered.simpletodo.features.dueDialog.presentation.components.timePicker

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import at.robthered.simpletodo.features.core.utils.ext.withHour
import at.robthered.simpletodo.features.core.utils.ext.withMinute
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class TimePickerState(
    initialInstant: Instant?,
    private val onSave: (instant: Instant) -> Unit,
    private val onClear: () -> Unit,
) {
    var currentInstant by mutableStateOf(initialInstant ?: Clock.System.now())
        private set

    var isVisible by mutableStateOf(false)
        private set

    fun onShow() {
        isVisible = true
    }

    fun onHide() {
        isVisible = false
    }

    fun setHour(hour: Int) {
        currentInstant = currentInstant.withHour(hour)
    }
    fun setMinute(minute: Int){
        currentInstant = currentInstant.withMinute(minute)
    }
    fun save() {
        onHide()
        onSave(currentInstant)
    }
    fun clear() {
        onClear()
        onHide()
    }
}