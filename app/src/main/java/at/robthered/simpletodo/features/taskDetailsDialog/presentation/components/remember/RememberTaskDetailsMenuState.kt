package at.robthered.simpletodo.features.taskDetailsDialog.presentation.components.remember

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun rememberTaskDetailsMenuState(
    onNavigateToTaskActivityDialog: () -> Unit,
    onOpenUpdateTaskModal: () -> Unit,
    onOpenDeleteDialog: () -> Unit,
): TaskDetailsMenuState {
    val deleteTextColor = MaterialTheme.colorScheme.onSurface
    val deleteIconTint = MaterialTheme.colorScheme.error.copy(alpha = 0.6f)
    val deleteBackgroundColor = MaterialTheme.colorScheme.error.copy(alpha = 0.2f)
    return remember {
        TaskDetailsMenuState(
            onNavigateToTaskActivityDialog = onNavigateToTaskActivityDialog,
            onOpenDeleteDialog = onOpenDeleteDialog,
            deleteTextColor = deleteTextColor,
            deleteIconTint = deleteIconTint,
            deleteBackgroundColor = deleteBackgroundColor,
            onOpenUpdateTaskModal = onOpenUpdateTaskModal,
        )
    }
}