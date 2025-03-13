package at.robthered.simpletodo.features.core.presentation.components.taskLinks.remember

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun rememberTaskLinksDialogState(
    initVisible: Boolean = false,
    onAddItemClick: () -> Unit,
    onDismissRequest: () -> Unit,
): TasksLinkDialogState {
    return remember {
        TasksLinkDialogState(
            initVisible = initVisible,
            onAddItemClick = onAddItemClick,
            onDismissRequest = onDismissRequest,
        )
    }
}