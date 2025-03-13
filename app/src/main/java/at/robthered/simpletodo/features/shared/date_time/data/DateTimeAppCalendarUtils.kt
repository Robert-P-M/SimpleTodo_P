package at.robthered.simpletodo.features.shared.date_time.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import at.robthered.simpletodo.features.core.utils.ext.isoWeekNumber
import at.robthered.simpletodo.features.shared.date_time.data.mapper.toAppDate
import at.robthered.simpletodo.features.shared.date_time.data.mapper.toLocalDate
import at.robthered.simpletodo.features.shared.date_time.domain.AppCalendarUtils
import at.robthered.simpletodo.features.shared.date_time.domain.AppDateTimeRepository
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppCalendarDayItem
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppCalendarMonth
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppCalendarWeek
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppDate
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppDateTime
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppDayOfWeek
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppUpcomingDate
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppUpcomingDateEnum
import at.robthered.simpletodo.features.shared.date_time.presentation.CalendarMonthPagingSource
import at.robthered.simpletodo.features.shared.locale.data.mapper.toLocale
import at.robthered.simpletodo.features.shared.locale.domain.AppLocaleProvider
import at.robthered.simpletodo.features.shared.locale.domain.model.AppLocale
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import java.time.format.TextStyle

class DateTimeAppCalendarUtils(
    private val localeProvider: AppLocaleProvider,
    private val appDateTimeRepository: AppDateTimeRepository,
    ): AppCalendarUtils {

    override  fun getAllBoundingDays(currentAppDate: AppDate): List<AppDate> {
        val currentDate = appDateTimeRepository.getCurrentDate()
        val firstDayOfMonth =
            LocalDate(
                year = currentAppDate.year,
                monthNumber = currentAppDate.monthNumber,
                dayOfMonth = 1
            )
        val lastDayOfMonth =
            firstDayOfMonth.plus(DatePeriod(months = 1)).minus(DatePeriod(days = 1))
        val firstDay =
            firstDayOfMonth.minus(DatePeriod(days = (firstDayOfMonth.dayOfWeek.ordinal % 7)))
        val lastDay =
            lastDayOfMonth.plus(DatePeriod(days = (6 - lastDayOfMonth.dayOfWeek.ordinal % 7)))
        return generateSequence(firstDay) { it.plus(DatePeriod(days = 1)) }
            .takeWhile { it <= lastDay  }
            .filter { it.isoWeekNumber() >= currentDate.isoWeekNumber }
            .toList()
            .map { it.toAppDate(appLocale = localeProvider.getLocale()) }
    }

    override fun getNextWeekendDay(date: AppDate): AppDate? {
        var currentDate = date
        repeat(14) {
            if (currentDate.isoDayNumber in listOf(6, 7)) {
                return if (currentDate.isoDayNumber == 6) currentDate
                else appDateTimeRepository.plusDays(currentDate, -1)
            }
            currentDate = appDateTimeRepository.plusDays(currentDate, 1)
        }
        return null
    }

    override fun getNextMonday(date: AppDate): AppDate {
        var currentDate = date
                while (currentDate.isoDayNumber != 1) {
                    currentDate = appDateTimeRepository.plusDays(currentDate, 1)
                }
        return currentDate
    }

    override fun getCalendarMonth(currentAppDate: AppDate): AppCalendarMonth {
        val weeks = getWeeksForMonth(currentAppDate)
        val calendarWeeks: List<AppCalendarWeek> = weeks.map { week ->
            listOf(
                AppCalendarDayItem.AppCalendarWeek(
                    isoWeekNumber = week.first().isoWeekNumber
                )
            ) + week.map { day -> AppCalendarDayItem.AppCalendarDay(day = day) }
        }

        return AppCalendarMonth(
            monthNumber = currentAppDate.monthNumber,
            year = currentAppDate.year,
            weeks = calendarWeeks
        )
    }
    private fun getWeeksForMonth(currentAppDate: AppDate): List<List<AppDate>> {
        val boundaryDays = getAllBoundingDays(currentAppDate)
        return boundaryDays.groupBy { it.isoWeekNumber }
            .values
            .toList()
    }

    override fun getUpcomingDays(
        currentAppDateTime: AppDateTime,
        locale: AppLocale,
    ): Flow<List<AppUpcomingDate>> {
        val today = currentAppDateTime.date
        val tomorrow = appDateTimeRepository.plusDays(today, 1)

        val nextMonday = getNextMonday(today)
        val thisWeekend = getNextWeekendDay(today)
        val nextWeekend = getNextWeekendDay(nextMonday)

        val upcomingDates = mutableListOf<AppUpcomingDate>().apply {
            add(AppUpcomingDate(today, AppUpcomingDateEnum.TODAY))

            if (tomorrow.isoDayNumber !in listOf(6, 7)) {
                add(AppUpcomingDate(tomorrow, AppUpcomingDateEnum.TOMORROW))
            }

            thisWeekend?.takeIf { it != today && it != tomorrow }?.let {
                add(AppUpcomingDate(it, AppUpcomingDateEnum.THIS_WEEKEND))
            }

            add(AppUpcomingDate(nextMonday, AppUpcomingDateEnum.NEXT_WEEK))


            nextWeekend?.let {
                add(AppUpcomingDate(it, AppUpcomingDateEnum.NEXT_WEEKEND))
            }
        }

        return flowOf(upcomingDates.distinctBy { it.appDate })
    }

    override fun getPagedMonth(): Flow<PagingData<AppCalendarMonth>> {
        return Pager(
            config = PagingConfig(
                pageSize = 3,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                CalendarMonthPagingSource(
                    appCalendarUtils = this,
                    appDateTimeRepository = appDateTimeRepository,
                    initialDate = appDateTimeRepository.getCurrentDateTime().date,
                )
            }
        ).flow
    }

    override fun getFormattedDaysOfWeek(appLocale: AppLocale): Flow<List<AppDayOfWeek>> = flow {
        val daysOfWeek = DayOfWeek.entries.map { dayOfWeek ->
            AppDayOfWeek(
                isoDayNumber = dayOfWeek.isoDayNumber,
                name = dayOfWeek.getDisplayName(TextStyle.FULL, appLocale.toLocale())
            )
        }
        emit(daysOfWeek)
    }

}