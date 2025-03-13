package at.robthered.simpletodo.features.dueDialog.presentation.ext

import at.robthered.simpletodo.features.core.utils.ext.toLocalDate
import at.robthered.simpletodo.features.dataSource.domain.local.model.EventModel
import at.robthered.simpletodo.features.dueDialog.presentation.components.calendarDay.CalendarDayUiModel
import at.robthered.simpletodo.features.shared.date_time.data.mapper.toLocalDate
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppCalendarDayItem
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppDate

fun AppCalendarDayItem.AppCalendarDay.toUiModel(
    currentAppDate: AppDate,
    monthNumber: Int,
    eventModel: EventModel,
    isEventActive: Boolean,
    onSetDate: () -> Unit,
): CalendarDayUiModel {
    return CalendarDayUiModel(
        isCurrentDay = this.day.toLocalDate() == currentAppDate.toLocalDate(),
        isPickedDay = this.day.toLocalDate() == eventModel.start.toLocalDate() && isEventActive,
        isEnabled = this.day.toLocalDate() >= currentAppDate.toLocalDate(),
        itemAlpha = this.getAlpha(monthNumber = monthNumber, currentAppDate = currentAppDate),
        text = this.day.dayOfMonth.toString().padStart(2, '0'),
        onClick = onSetDate
    )
}

fun AppCalendarDayItem.AppCalendarDay.getAlpha(monthNumber: Int, currentAppDate: AppDate): Float {
    return when {
        day.monthNumber != monthNumber  -> 0.4f
        day.isoDayNumber == 6 -> 0.7f
        day.isoDayNumber == 7 -> 0.6f
        day.toLocalDate() < currentAppDate.toLocalDate() -> 0.4f
        else -> 1f
    }
}