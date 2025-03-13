package at.robthered.simpletodo.features.dueDialog.presentation.components.timePicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

@Composable
fun rememberTimePickerState(
    initialInstant: Instant? = Clock.System.now(),
    onSave: (instant: Instant) -> Unit,
    onClear: () -> Unit
): TimePickerState {
    return remember {
        TimePickerState(
            initialInstant = initialInstant,
            onSave = onSave,
            onClear = onClear
        )
    }
}