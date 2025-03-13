package at.robthered.simpletodo.features.core.utils.ext

import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char

object LocalDateFormats {

    val DayOfWeek = LocalDate.Format {
        dayOfWeek(DayOfWeekNames.ENGLISH_ABBREVIATED)
    }
    val UpcomingDateDayOfWeekWithDate = LocalDate.Format {
        dayOfWeek(DayOfWeekNames.ENGLISH_ABBREVIATED)
        chars(" (")
        dayOfMonth()
        chars(". ")
        monthName(MonthNames.ENGLISH_ABBREVIATED)
        chars(")")
    }
    val DayOfWeekWithDate = LocalDate.Format {
        dayOfWeek(DayOfWeekNames.ENGLISH_FULL)
        chars(" ")
        dayOfMonth()
        char('.')
        monthName(MonthNames.ENGLISH_ABBREVIATED)
        chars(" ")
        year()
    }

    val MonthShortFormat = LocalDate.Format {
        monthName(MonthNames.ENGLISH_ABBREVIATED)
    }
    val MonthLongYearFormat = LocalDate.Format {
        monthName(MonthNames.ENGLISH_FULL)
        chars(" ")
        year()
    }

    val DateFormat = LocalDate.Format {
        dayOfMonth()
        chars(". ")
        monthName(MonthNames.ENGLISH_FULL)
        char(' ')
        year()
    }



}