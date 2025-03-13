package at.robthered.simpletodo.features.core.presentation.components.taskLinks.remember

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class TasksLinkDialogState(
    initVisible: Boolean,
    val onAddItemClick: () -> Unit,
    val onDismissRequest: () -> Unit,
) {
    var isVisible by mutableStateOf(initVisible)
        private set

    fun onShow() {
        isVisible = true
    }
    fun onHide() {
        isVisible = false
        onDismissRequest()
    }

}