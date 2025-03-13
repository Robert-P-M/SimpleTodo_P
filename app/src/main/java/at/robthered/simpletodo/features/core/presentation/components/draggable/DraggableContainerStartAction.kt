package at.robthered.simpletodo.features.core.presentation.components.draggable

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.onGloballyPositioned

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun BoxScope.DraggableContainerStartAction(
    dragState: DraggableContainerState,
    content: @Composable RowScope.() -> Unit,
) {
    val startActionAlpha by animateFloatAsState(
        targetValue = if (!dragState.dragState.isAnimationRunning && dragState.dragState.settledValue == DragAnchors.MOVE_TO_END) 1f else 0f
    )
    Row(
        modifier = Modifier
            .alpha(startActionAlpha)
            .align(Alignment.CenterStart)
            .onGloballyPositioned {
                dragState.setSwipeActionSize(it.size.width)
            }
    ) {
        content()
    }
}