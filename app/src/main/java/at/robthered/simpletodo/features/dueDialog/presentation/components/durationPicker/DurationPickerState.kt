package at.robthered.simpletodo.features.dueDialog.presentation.components.durationPicker

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class DurationPickerState(
    private val initialMinutes: Int?,
    private val onSave: (minutes: Int) -> Unit,
    private val onClear: () -> Unit,
) {



    var currentHour by mutableIntStateOf(0)
    var currentMinute by mutableIntStateOf(0)


    init {
        initializeValues()
    }

    private fun initializeValues() {
        initialMinutes?.let { minutes ->
            currentHour = minutes / 60
            currentMinute = minutes % 60
        }
    }


    var isVisible by mutableStateOf(false)
        private set

    fun onShow() {
        isVisible = true
    }

    fun onHide() {
        isVisible = false
    }

    fun setHour(hour: Int) {
        currentHour = hour.coerceIn(0, 23)
    }
    fun setMinute(minute: Int) {
        currentMinute = minute.coerceIn(0,59)
    }

    private fun newMinutes() : Int {
        return currentHour.times(60)  + currentMinute
    }
    fun save() {
        onHide()
        if(newMinutes() > 0) {
            onSave(newMinutes())
        }
    }

    fun reset() {
        initializeValues()
        onClear()
        onHide()
    }

}