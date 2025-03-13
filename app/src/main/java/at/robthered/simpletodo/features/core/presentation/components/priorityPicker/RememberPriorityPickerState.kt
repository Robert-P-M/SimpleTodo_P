package at.robthered.simpletodo.features.core.presentation.components.priorityPicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityEnum

@Composable
fun rememberPriorityPickerState(
    initVisible: Boolean = false,
    initPriority: PriorityEnum?,
    onPickPriority: (priority: PriorityEnum?) -> Unit,
    onDismissRequest: () -> Unit
): PriorityPickerState {
    return remember {
        PriorityPickerState(
            initVisible = initVisible,
            initPriority = initPriority,
            onPickPriority = onPickPriority,
            onDismissRequest = onDismissRequest
        )
    }
}