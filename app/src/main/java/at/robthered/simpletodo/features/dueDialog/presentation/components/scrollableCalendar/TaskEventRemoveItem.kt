package at.robthered.simpletodo.features.dueDialog.presentation.components.scrollableCalendar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.robthered.simpletodo.features.core.domain.state_manager.dueEvent.EventState
import at.robthered.simpletodo.features.core.utils.ext.toDayOfWeekWithDate
import at.robthered.simpletodo.features.core.utils.ext.toFormattedDateTimeWithDayOfWeek
import at.robthered.simpletodo.features.core.utils.ext.toLocalDate
import at.robthered.simpletodo.features.dueDialog.presentation.components.SelectedUpcomingEndItem
import at.robthered.simpletodo.features.dueDialog.presentation.components.SelectedUpcomingStartItem


fun LazyListScope.taskEventRemoveItem(
    onRemoveStartClick: () -> Unit,
    onRemoveEndClick: () -> Unit,
    eventState: EventState,
) {

        item {
            AnimatedVisibility(
                visible = eventState.isEventActive,
            ) {
                    SelectedUpcomingStartItem(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        eventStart =  if(eventState.eventModel.isWithTime) {
                            eventState.eventModel.start.toFormattedDateTimeWithDayOfWeek()
                        } else {
                            eventState.eventModel.start.toLocalDate().toDayOfWeekWithDate()
                        },
                        isEndAvailable = eventState.eventModel.end != null,
                        onClick = onRemoveStartClick,
                        withDelete = true
                    )
            }
            AnimatedVisibility(
                visible = eventState.isEventActive && eventState.eventModel.end != null
            ) {
                eventState.eventModel.end?.let { end ->
                    SelectedUpcomingEndItem(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        eventEnd = end.toFormattedDateTimeWithDayOfWeek(),
                        onClick = onRemoveEndClick,
                        withDelete = true
                    )
                }
            }
        }



}