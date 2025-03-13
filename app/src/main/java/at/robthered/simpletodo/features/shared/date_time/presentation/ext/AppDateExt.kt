package at.robthered.simpletodo.features.shared.date_time.presentation.ext

import androidx.compose.runtime.Composable
import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.presentation.UiText
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppCalendarMonth
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppDate

@Composable
fun AppDate.toDayOfWeek(take: Int = 3): String {
    return this.getDayOfWeekName().asString().take(take)
}

fun AppCalendarMonth.getMonthName(): UiText {
    val monthNames = listOf(
        R.string.month_name_january,
        R.string.month_name_february,
        R.string.month_name_march,
        R.string.month_name_april,
        R.string.month_name_may,
        R.string.month_name_june,
        R.string.month_name_july,
        R.string.month_name_august,
        R.string.month_name_september,
        R.string.month_name_october,
        R.string.month_name_november,
        R.string.month_name_december,
    )
    return UiText.StringResourceId(id = monthNames[this.monthNumber -1])
}

fun AppDate.getDayOfWeekName(): UiText {
    val dayNames = listOf(
        R.string.day_of_week_name_monday,
        R.string.day_of_week_name_tuesday,
        R.string.day_of_week_name_wednesday,
        R.string.day_of_week_name_thursday,
        R.string.day_of_week_name_friday,
        R.string.day_of_week_name_saturday,
        R.string.day_of_week_name_sunday,
    )
    return UiText.StringResourceId(id = dayNames[this.isoDayNumber -1])
}

fun AppDate.getMonthName(): UiText {
    val monthNames = listOf(
        R.string.month_name_january,
        R.string.month_name_february,
        R.string.month_name_march,
        R.string.month_name_april,
        R.string.month_name_may,
        R.string.month_name_june,
        R.string.month_name_july,
        R.string.month_name_august,
        R.string.month_name_september,
        R.string.month_name_october,
        R.string.month_name_november,
        R.string.month_name_december,
    )
    return UiText.StringResourceId(id = monthNames[this.monthNumber -1])
}

@Composable
fun AppDate.toDayOfWeekWithDate(): String {
    val dayOfWeekName = this.toDayOfWeek(2)
    val dayOfMonth = this.dayOfMonth
    val monthName = this.getMonthName()

    return "$dayOfWeekName ($dayOfMonth.${monthName.asString().take(3)})"
}

@Composable
fun AppDate.toLongDayOfWeekWithDate(): String {
    val dayOfWeekName = this.getDayOfWeekName().asString()
    val dayOfMonth = this.dayOfMonth
    val monthName = this.getMonthName().asString()

    return "$dayOfWeekName ($dayOfMonth.${monthName})"
}