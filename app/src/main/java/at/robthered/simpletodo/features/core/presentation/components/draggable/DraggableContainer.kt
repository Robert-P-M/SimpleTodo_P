package at.robthered.simpletodo.features.core.presentation.components.draggable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun DraggableContainer(
    modifier: Modifier = Modifier,
    dragState: DraggableContainerState = rememberDraggableContainerState(),
    startContent: (@Composable BoxScope.(
        dragState: DraggableContainerState,
    ) -> Unit)? = null,
    endContent: (@Composable BoxScope.(
        dragState: DraggableContainerState,
    ) -> Unit)? = null,
    content: @Composable (
        dragState: DraggableContainerState,
    ) -> Unit,
) {

    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        startContent?.invoke(this, dragState)
        endContent?.invoke(this, dragState)
        content(dragState)
    }
}