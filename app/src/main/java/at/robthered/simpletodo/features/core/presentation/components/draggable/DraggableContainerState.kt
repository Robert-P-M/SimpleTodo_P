package at.robthered.simpletodo.features.core.presentation.components.draggable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState

class DraggableContainerState @OptIn(ExperimentalFoundationApi::class) constructor(
    val dragState: AnchoredDraggableState<DragAnchors>,
    val setSwipeActionSize: (Int) -> Unit,
    val animateToResting: () -> Unit,
)