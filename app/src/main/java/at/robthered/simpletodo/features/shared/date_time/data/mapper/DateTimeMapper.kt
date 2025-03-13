package at.robthered.simpletodo.features.shared.date_time.data.mapper

import at.robthered.simpletodo.features.core.utils.ext.isoWeekNumber
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppDate
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppDateTime
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppTime
import at.robthered.simpletodo.features.shared.locale.domain.model.AppLocale
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.isoDayNumber

fun AppTime.toLocalTime(): LocalTime {
    return LocalTime(
        hour = this.hour,
        minute = this.minute,
        second = this.second
    )
}

fun LocalTime.toAppTime(): AppTime {
    return AppTime(
        hour = this.hour,
        minute = this.minute,
        second = this.second
    )
}

fun LocalDate.toAppDate(
    appLocale: AppLocale
) : AppDate {
    return AppDate(
        dayOfMonth = this.dayOfMonth,
        isoDayNumber = this.dayOfWeek.isoDayNumber,
        monthNumber = this.monthNumber,
        year = this.year,
        isoWeekNumber = this.isoWeekNumber()
    )
}

fun AppDate.toLocalDate(): LocalDate {
    return LocalDate(
        dayOfMonth = this.dayOfMonth,
        monthNumber = this.monthNumber,
        year = this.year
    )
}

fun LocalDateTime.toAppDateTime(appLocale: AppLocale): AppDateTime {
    return AppDateTime(
        date = AppDate(
            dayOfMonth = this.dayOfMonth,
            isoDayNumber = this.dayOfWeek.isoDayNumber,
            monthNumber = this.monthNumber,
            year = this.year,
            isoWeekNumber = this.date.isoWeekNumber(),
        ),
        time = AppTime(
            hour = this.hour,
            minute = this.minute,
            second = this.second,
        ),
        /*epochMillis = this.date.toInstant().toEpochMilliseconds() + this.time.toMillisecondOfDay()*/
    )
}

fun AppDateTime.toLocalDateTime(): LocalDateTime {
    return LocalDateTime(
        year = this.date.year,
        monthNumber = this.date.monthNumber,
        dayOfMonth = this.date.dayOfMonth,
        hour = this.time.hour,
        minute = this.time.minute,
    )
}