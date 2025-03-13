package at.robthered.simpletodo.features.homeScreen.presentation.components.remember

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import at.robthered.simpletodo.features.dataSource.presentation.model.color
import at.robthered.simpletodo.features.dataSource.presentation.model.icon
import at.robthered.simpletodo.features.dataSource.presentation.model.toUiText
import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityModel
import kotlinx.datetime.Instant

@Composable
fun rememberTaskCardMenuState(
    taskId: Long?,
    createdAt: Instant,
    currentPriority: PriorityModel? = null,
    onNavigateToUpdateTaskDialog: () -> Unit,
    onAddSubtaskClick: () -> Unit,
    onNavigateToDetails: () -> Unit,
    onOpenPriorityPicker: () -> Unit,
    onOpenDeleteDialog: () -> Unit,
    isMaxDepthReached: Boolean,
    onNavigateToTaskActivityLogDialog: () -> Unit,
) : TaskCardMenuState {

    val priorityText = currentPriority.toUiText()
    val priorityIcon = currentPriority.icon()
    val priorityColor = currentPriority.color()
    val deleteTextColor = MaterialTheme.colorScheme.onSurface
    val deleteIconTint = MaterialTheme.colorScheme.error.copy(alpha = 0.6f)
    val deleteBackgroundColor = MaterialTheme.colorScheme.error.copy(alpha = 0.2f)
    return remember(taskId) {
        TaskCardMenuState(
            createdAt = createdAt,
            onOpenUpdateTaskModal = onNavigateToUpdateTaskDialog,
            onNavigateToTaskActivityLogDialog = onNavigateToTaskActivityLogDialog,
            onAddSubtaskClick = onAddSubtaskClick,
            onOpenPriorityPicker = onOpenPriorityPicker,
            onNavigateToDetails = onNavigateToDetails,
            isMaxDepthReached = isMaxDepthReached,
            deleteTextColor = deleteTextColor,
            deleteIconTint = deleteIconTint,
            deleteBackgroundColor = deleteBackgroundColor,
            priorityText = priorityText,
            priorityIcon = priorityIcon,
            priorityColor = priorityColor,
            onOpenDeleteDialog = onOpenDeleteDialog,
        )
    }
}

@Composable
fun rememberDeleteDialogState(
    handleDelete: () -> Unit,
): DeleteDialogState {
    return remember {
        DeleteDialogState(
            handleDelete = handleDelete
        )
    }
}