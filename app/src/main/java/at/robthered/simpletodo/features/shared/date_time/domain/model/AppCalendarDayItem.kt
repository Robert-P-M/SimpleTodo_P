package at.robthered.simpletodo.features.shared.date_time.domain.model

sealed class AppCalendarDayItem {
    data class AppCalendarWeek(
        val isoWeekNumber: Int,
    ): AppCalendarDayItem()
    data class AppCalendarDay(
        val day: AppDate
    ): AppCalendarDayItem()
}