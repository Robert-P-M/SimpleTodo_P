package at.robthered.simpletodo.features.dueDialog.presentation.components.durationPicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun rememberDurationPickerState(
    initialMinutes: Int? = 0,
    onSave: (minutes: Int) -> Unit,
    onClear: () -> Unit,
): DurationPickerState {
    return remember {
        DurationPickerState(
            initialMinutes = initialMinutes,
            onSave = onSave,
            onClear = onClear
        )
    }

}