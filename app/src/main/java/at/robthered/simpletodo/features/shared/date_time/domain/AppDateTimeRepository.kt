package at.robthered.simpletodo.features.shared.date_time.domain

import at.robthered.simpletodo.features.shared.date_time.domain.model.AppDateTime
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppDate
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppTime

interface AppDateTimeRepository {
    fun getCurrentDateTime(): AppDateTime
    fun getCurrentDate(): AppDate
    fun plusDays(appDate: AppDate, count: Int): AppDate
    fun plusDays(appDateTime: AppDateTime, count: Int): AppDateTime
    fun minusDays(appDate: AppDate, count: Int): AppDate
    fun minusDays(appDateTime: AppDateTime, count: Int): AppDateTime
    fun plusMonths(appDate: AppDate, count: Int): AppDate
    fun plusMonths(appDateTime: AppDateTime, count: Int): AppDateTime
    fun minusMonth(appDate: AppDate, count: Int): AppDate
    fun minusMonths(appDateTime: AppDateTime, count: Int): AppDateTime
    fun setHour(appDateTime: AppDateTime, hour: Int): AppDateTime
    fun setMinute(appDateTime: AppDateTime, minute: Int): AppDateTime
    fun setTime(appDateTime: AppDateTime, appTime: AppTime): AppDateTime
}