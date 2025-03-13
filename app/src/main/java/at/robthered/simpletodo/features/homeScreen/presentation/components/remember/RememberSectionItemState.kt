package at.robthered.simpletodo.features.homeScreen.presentation.components.remember

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.datetime.Instant

@Composable
fun rememberSectionHeaderMenuState(
    sectionId: Long?,
    createdAt: Instant,
    onOpenUpdateSectionModal: () -> Unit,
    onAddTaskClick: () -> Unit,
    onOpenDeleteDialog: () -> Unit,
    ): SectionHeaderMenuState {

    val deleteTextColor = MaterialTheme.colorScheme.onSurface
    val deleteIconTint = MaterialTheme.colorScheme.error.copy(alpha = 0.6f)
    val deleteBackgroundColor = MaterialTheme.colorScheme.error.copy(alpha = 0.2f)
    return remember(sectionId) {
        SectionHeaderMenuState(
            createdAt = createdAt,
            onOpenUpdateSectionModal = onOpenUpdateSectionModal,
            onOpenDeleteDialog = onOpenDeleteDialog,
            onAddTaskClick = onAddTaskClick,
            deleteTextColor = deleteTextColor,
            deleteIconTint = deleteIconTint,
            deleteBackgroundColor = deleteBackgroundColor
        )
    }
}