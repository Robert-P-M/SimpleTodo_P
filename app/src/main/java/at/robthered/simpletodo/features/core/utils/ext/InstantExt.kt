package at.robthered.simpletodo.features.core.utils.ext

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration.Companion.minutes

fun Instant.toLong() = this.toEpochMilliseconds()
fun Instant.toFormattedForMenuHeader(): String {
    val timeZone = TimeZone.currentSystemDefault()
    val localDateTime = this.toLocalDateTime(timeZone)
    return LocalDateTimeFormats.MenuHeaderFormat.format(localDateTime)
}

fun Instant.toFormattedDateTimeWithDayOfWeek(): String {
    val timeZone = TimeZone.currentSystemDefault()
    val localDateTime = this.toLocalDateTime(timeZone)
    return LocalDateTimeFormats.DateTimeFormatWithWeekday.format(localDateTime)
}
fun Instant.toFormattedDateTime(): String {
    val timeZone = TimeZone.currentSystemDefault()
    val localDateTime = this.toLocalDateTime(timeZone)
    return LocalDateTimeFormats.DateTimeFormat.format(localDateTime)
}

fun Instant.toFormattedDate(): String {
    val timeZone = TimeZone.currentSystemDefault()
    val localDateTime = this.toLocalDateTime(timeZone)
    return LocalDateTimeFormats.DateFormat.format(localDateTime)
}

fun Instant.toLocalDate(): LocalDate {
    return this.toLocalDateTime(TimeZone.currentSystemDefault()).date
}

fun Instant.getHour(timeZone: TimeZone = TimeZone.currentSystemDefault()): Int {
    return this.toLocalDateTime(timeZone = timeZone).hour
}

fun Instant.getMinute(timeZone: TimeZone = TimeZone.currentSystemDefault()): Int {
    return this.toLocalDateTime(timeZone = timeZone).minute
}

fun Instant.getTime(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalTime {
    return this.toLocalDateTime(timeZone = timeZone).time
}

fun Instant.getLocalDate(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDate {
    return this.toLocalDateTime(timeZone = timeZone).date
}


fun Instant.withHour(hour: Int, timeZone: TimeZone = TimeZone.currentSystemDefault()): Instant {
    val localDateTime = this.toLocalDateTime(timeZone = timeZone)
    return LocalDateTime(
        date = localDateTime.date,
        time = LocalTime(hour = hour, minute = localDateTime.minute)
    ).toInstant(timeZone = timeZone)
}

fun Instant.withMinute(minute: Int, timeZone: TimeZone = TimeZone.currentSystemDefault()): Instant {
    val localDateTime = this.toLocalDateTime(timeZone = timeZone)
    return LocalDateTime(
        date = localDateTime.date,
        time = LocalTime(localDateTime.hour, minute = minute)
    ).toInstant(
        timeZone = timeZone
    )
}


fun Instant.withDate(
    localDateTime: LocalDateTime,
    timeZone: TimeZone = TimeZone.currentSystemDefault(),
): Instant {
    return LocalDateTime(
        date = localDateTime.date,
        time = localDateTime.time
    ).toInstant(timeZone = timeZone)
}



fun Int.toLocalTime(): LocalTime {
    val durationMinutes = this.minutes

    val hours = durationMinutes.inWholeHours.toInt()
    val minutes = durationMinutes.inWholeMinutes.toInt() - (hours * 60)
    return LocalTime(hour = hours, minute = minutes)
}