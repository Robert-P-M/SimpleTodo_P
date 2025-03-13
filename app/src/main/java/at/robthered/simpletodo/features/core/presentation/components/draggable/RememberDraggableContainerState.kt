package at.robthered.simpletodo.features.core.presentation.components.draggable

import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun rememberDraggableContainerState(
    isStartEnabled: Boolean = true,
    isEndEnabled: Boolean = true,
): DraggableContainerState {
    val density = LocalDensity.current


    var swipeActionSize by remember {
        mutableIntStateOf(100)
    }
    val anchors = remember(density, swipeActionSize, isStartEnabled, isEndEnabled) {
        DraggableAnchors {
            if (isStartEnabled) {
                DragAnchors.MOVE_TO_END at with(density) { swipeActionSize.toDp().toPx() }
            }
            DragAnchors.RESTING at 0f
            if (isEndEnabled) {
                DragAnchors.MOVE_TO_START at -with(density) { swipeActionSize.toDp().toPx() }
            }
        }
    }
    val dragState = remember(density) {
        AnchoredDraggableState(
            initialValue = DragAnchors.RESTING,
            anchors = anchors,
            positionalThreshold = { totalDistance: Float ->
                totalDistance
            },
            velocityThreshold = { with(density) { swipeActionSize.dp.toPx() } },
            decayAnimationSpec = exponentialDecay(
                absVelocityThreshold = 0.4f,
                frictionMultiplier = 0.4f
            ),
            snapAnimationSpec = tween(),
        )
    }

    SideEffect { dragState.updateAnchors(anchors) }
    val coroutineScope = rememberCoroutineScope()

    return remember(density, dragState) {
        DraggableContainerState(
            dragState = dragState,
            setSwipeActionSize = { swipeActionSize = it },
            animateToResting = {
                coroutineScope.launch {
                    delay(2500)
                    dragState.animateTo(DragAnchors.RESTING)
                }
            }
        )
    }.apply {
        LaunchedEffect(this) {
            snapshotFlow { dragState.settledValue }
                .collectLatest {
                    if (it == DragAnchors.MOVE_TO_START || it == DragAnchors.MOVE_TO_END) {
                        animateToResting()
                    }
                }
        }
    }
}