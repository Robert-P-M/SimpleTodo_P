package at.robthered.simpletodo.features.homeScreen.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Unarchive
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import at.robthered.simpletodo.features.core.presentation.components.draggable.DraggableContainer
import at.robthered.simpletodo.features.core.presentation.components.draggable.DraggableContainerEndAction
import at.robthered.simpletodo.features.core.presentation.components.draggable.DraggableContainerStartAction
import at.robthered.simpletodo.features.core.presentation.components.draggable.DraggableContainerState

@Composable
fun SwipeableTaskCard(
    modifier: Modifier = Modifier,
    isTaskArchived: Boolean,
    onToggleIsArchived: (dragState: DraggableContainerState) -> Unit,
    onDeleteTask: (dragState: DraggableContainerState) -> Unit,
    taskCard: @Composable (dragState: DraggableContainerState) -> Unit,
) {

    DraggableContainer(
        modifier = modifier,
        startContent = { dragState: DraggableContainerState ->
            DraggableContainerStartAction(
                dragState = dragState,
            ) {
                IconButton(
                    onClick = {
                        onToggleIsArchived(dragState)
                    }
                ) {
                    Icon(
                        imageVector = if (isTaskArchived) Icons.Outlined.Unarchive else Icons.Outlined.Archive,
                        contentDescription = null
                    )
                }
            }
        },
        endContent = { dragState: DraggableContainerState ->
            DraggableContainerEndAction(
                dragState = dragState,
            ) {
                IconButton(
                    onClick = {
                        onDeleteTask(dragState)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = null
                    )
                }
            }
        }
    ) { dragState: DraggableContainerState ->
        taskCard(dragState)
    }
}