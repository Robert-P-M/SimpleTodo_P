package at.robthered.simpletodo.features.shared.date_time.domain

import androidx.paging.PagingData
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppDateTime
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppCalendarMonth
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppDate
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppDayOfWeek
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppUpcomingDate
import at.robthered.simpletodo.features.shared.locale.domain.model.AppLocale
import kotlinx.coroutines.flow.Flow

interface AppCalendarUtils {
    fun getAllBoundingDays(currentAppDate: AppDate): List<AppDate>
    fun getNextWeekendDay(
        date: AppDate
    ): AppDate?
    fun getNextMonday(
        date: AppDate
    ): AppDate
    fun getCalendarMonth(currentAppDate: AppDate): AppCalendarMonth
    fun getUpcomingDays(currentAppDateTime: AppDateTime, locale: AppLocale): Flow<List<AppUpcomingDate>>
    fun getPagedMonth(): Flow<PagingData<AppCalendarMonth>>
    fun getFormattedDaysOfWeek(appLocale: AppLocale): Flow<List<AppDayOfWeek>>
}