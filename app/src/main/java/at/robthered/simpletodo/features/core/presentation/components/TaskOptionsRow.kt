package at.robthered.simpletodo.features.core.presentation.components

import android.util.Log
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.robthered.simpletodo.features.dataSource.domain.local.model.EventModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityEnum

@Composable
fun TaskOptionsRow(
    linksCount: Int,
    onAddLinkToggle: (Boolean) -> Unit,
    priorityEnum: PriorityEnum?,
    onPriorityClick: (Boolean) -> Unit,
    taskEventModel: EventModel?,
    onEventToggleClick: (Boolean) -> Unit,
    onNotificationToggleClick: (Boolean) -> Unit,
) {
        val rowState = rememberLazyListState()
        val flingBehaviour =
            rememberSnapFlingBehavior(lazyListState = rowState, snapPosition = SnapPosition.Center)
        LazyRow(
            state = rowState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            flingBehavior = flingBehaviour,
        ) {
            item {
                AddLinkToggleButton(
                    modifier = Modifier,
                    count = linksCount,
                    onCheckedChanged = onAddLinkToggle,
                )
            }
            item {
                PriorityToggleButton(
                    modifier = Modifier,
                    priorityEnum = priorityEnum,
                    onCheckedChanged = onPriorityClick
                )
            }
            item {

                EventToggleButton(
                    taskEventModel = taskEventModel,
                    onCheckedChanged = onEventToggleClick
                )
            }
            item {
                NotificationToggleButton(
                    modifier = Modifier,
                    taskEventModel = taskEventModel,
                    onCheckedChanged = onNotificationToggleClick
                )
            }
        }
}