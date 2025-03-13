package at.robthered.simpletodo.features.shared.date_time.domain.model

data class AppDate(
    val dayOfMonth: Int,
    /*val dayOfWeekName: String,*/
    val isoDayNumber: Int,
    val monthNumber: Int,
    /*val monthName: String,*/
    val year: Int,
    val isoWeekNumber: Int,
)