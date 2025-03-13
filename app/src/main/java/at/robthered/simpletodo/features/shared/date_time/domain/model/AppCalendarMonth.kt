package at.robthered.simpletodo.features.shared.date_time.domain.model

data class AppCalendarMonth(
    val monthNumber: Int,
    val year: Int,
    val weeks: List<AppCalendarWeek>
)