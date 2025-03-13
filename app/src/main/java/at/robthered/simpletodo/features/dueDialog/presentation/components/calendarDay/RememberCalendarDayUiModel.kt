package at.robthered.simpletodo.features.dueDialog.presentation.components.calendarDay

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import at.robthered.simpletodo.features.dataSource.domain.local.model.EventModel
import at.robthered.simpletodo.features.dueDialog.presentation.ext.toUiModel
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppCalendarDayItem
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppDate

@Composable
fun rememberCalendarDayUiModel(
    appCalendarDayItem: AppCalendarDayItem.AppCalendarDay,
    currentAppDate: AppDate,
    monthNumber: Int,
    eventModel: EventModel,
    isEventActive: Boolean,
    onSetDate: () -> Unit
): CalendarDayUiModel {
    return remember(
        appCalendarDayItem,
        currentAppDate,
        monthNumber,
        eventModel,
        isEventActive,
        onSetDate
    ) {
        appCalendarDayItem.toUiModel(
            currentAppDate = currentAppDate,
            monthNumber = monthNumber,
            eventModel = eventModel,
            isEventActive = isEventActive,
            onSetDate = onSetDate
        )
    }
}