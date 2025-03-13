package at.robthered.simpletodo.features.core.utils.ext

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.format
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.plus

fun LocalDate.isoWeekNumber(): Int {
    val thursday = this.plus(DatePeriod(days = 3 - this.dayOfWeek.isoDayNumber))
    val firstThursdayOfYear = LocalDate(thursday.year, 1, 4)
    val startOfFirstIsoWeek = firstThursdayOfYear.minus(
        DatePeriod(days = firstThursdayOfYear.dayOfWeek.isoDayNumber - 1)
    )
    return ((thursday.toEpochDays() - startOfFirstIsoWeek.toEpochDays()) / 7 + 1).toInt()
}

fun LocalDate.toDayOfWeek(): String {
    return this.format(LocalDateFormats.DayOfWeek)
}

fun LocalDate.toUpcomingDateDayOfWeekWithDate(): String {
    return this.format(LocalDateFormats.UpcomingDateDayOfWeekWithDate)
}

fun LocalDate.toDayOfWeekWithDate(): String {
    return this.format(LocalDateFormats.DayOfWeekWithDate)
}

fun LocalDate.toMonthShort(): String {
    return this.format(LocalDateFormats.MonthShortFormat)
}

fun LocalDate.toMonthLongYear(): String {
    return this.format(LocalDateFormats.MonthLongYearFormat)
}

fun LocalDate.toFormat(): String {
    return this.format(LocalDateFormats.DateFormat)
}

fun LocalDate.toInstant(): Instant {
    return this.atStartOfDayIn(TimeZone.currentSystemDefault())
}