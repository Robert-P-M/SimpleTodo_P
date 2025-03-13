package at.robthered.simpletodo.features.core.utils.ext

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char

object LocalDateTimeFormats {
    val MenuHeaderFormat = LocalDateTime.Format {
        dayOfMonth()
        char('.')
        monthName(MonthNames.ENGLISH_FULL)
        char(' ')
        year()
        chars(" - ")
        char(' ')
        hour()
        char(':')
        minute()
    }

    val DateTimeFormatWithWeekday = LocalDateTime.Format {
        dayOfWeek(names = DayOfWeekNames.ENGLISH_ABBREVIATED)
        chars(". ")
        dayOfMonth()
        char('.')
        monthName(MonthNames.ENGLISH_ABBREVIATED)
        char(' ')
        year()
        chars(" - ")
        char(' ')
        hour()
        char(':')
        minute()
    }
    val DateTimeFormat = LocalDateTime.Format {
        dayOfMonth()
        char('.')
        monthName(MonthNames.ENGLISH_ABBREVIATED)
        char(' ')
        year()
        chars(" - ")
        char(' ')
        hour()
        char(':')
        minute()
    }
    val DateFormat = LocalDateTime.Format {
        dayOfMonth()
        char('.')
        monthName(MonthNames.ENGLISH_ABBREVIATED)
        char(' ')
        year()
    }
}