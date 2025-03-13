package at.robthered.simpletodo.features.shared.date_time.data

import at.robthered.simpletodo.features.shared.date_time.domain.model.AppDateTime
import at.robthered.simpletodo.features.shared.date_time.data.mapper.toAppDate
import at.robthered.simpletodo.features.shared.date_time.data.mapper.toAppDateTime
import at.robthered.simpletodo.features.shared.date_time.data.mapper.toLocalDate
import at.robthered.simpletodo.features.shared.date_time.data.mapper.toLocalDateTime
import at.robthered.simpletodo.features.shared.date_time.data.mapper.toLocalTime
import at.robthered.simpletodo.features.shared.date_time.domain.AppDateTimeRepository
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppDate
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppTime
import at.robthered.simpletodo.features.shared.locale.domain.AppLocaleProvider
import at.robthered.simpletodo.features.shared.time_zone.domain.AppTimeZoneProvider
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

class KotlinDateTimeRepositoryImpl(
    private val localeProvider: AppLocaleProvider,
    private val timeZoneProvider: AppTimeZoneProvider,
) : AppDateTimeRepository {

    override fun getCurrentDateTime(): AppDateTime {
        val now = Clock.System.now()
        val localDateTime =
            now.toLocalDateTime(timeZone = TimeZone.of(timeZoneProvider.getTimeZone().id))
        return localDateTime.toAppDateTime(appLocale = localeProvider.getLocale())
    }

    override fun getCurrentDate(): AppDate {
        val now = Clock.System.now()
        val localDateTime = now.toLocalDateTime(timeZone = TimeZone.of(timeZoneProvider.getTimeZone().id))
        return localDateTime.date.toAppDate(appLocale = localeProvider.getLocale())
    }

    override fun plusDays(appDate: AppDate, count: Int): AppDate {
        val localDate = appDate.toLocalDate().plus(count, DateTimeUnit.DAY)
        return localDate.toAppDate(appLocale = localeProvider.getLocale())
    }

    override fun plusDays(appDateTime: AppDateTime, count: Int): AppDateTime {
        val localDateTime = appDateTime.toLocalDateTime()
        return LocalDateTime(
            localDateTime.date.plus(count, DateTimeUnit.DAY),
            localDateTime.time
        ).toAppDateTime(appLocale = localeProvider.getLocale())
    }

    override fun minusDays(appDate: AppDate, count: Int): AppDate {
        val localDate = appDate.toLocalDate().minus(count, DateTimeUnit.DAY)
        return localDate.toAppDate(appLocale = localeProvider.getLocale())
    }

    override fun minusDays(appDateTime: AppDateTime, count: Int): AppDateTime {
        val localDateTime = appDateTime.toLocalDateTime()
        return LocalDateTime(
            localDateTime.date.minus(count, DateTimeUnit.DAY),
            localDateTime.time
        ).toAppDateTime(appLocale = localeProvider.getLocale())
    }

    override fun plusMonths(appDate: AppDate, count: Int): AppDate {
        val localDate = appDate.toLocalDate().plus(count, DateTimeUnit.MONTH)
        return localDate.toAppDate(appLocale = localeProvider.getLocale())
    }

    override fun plusMonths(appDateTime: AppDateTime, count: Int): AppDateTime {
        val localDateTime = appDateTime.toLocalDateTime()
        return LocalDateTime(
            localDateTime.date.plus(count, DateTimeUnit.MONTH),
            localDateTime.time
        ).toAppDateTime(appLocale = localeProvider.getLocale())
    }

    override fun minusMonth(appDate: AppDate, count: Int): AppDate {
        val localDate = appDate.toLocalDate().minus(count, DateTimeUnit.MONTH)
        return localDate.toAppDate(appLocale = localeProvider.getLocale())
    }

    override fun minusMonths(appDateTime: AppDateTime, count: Int): AppDateTime {
        val localDateTime = appDateTime.toLocalDateTime()
        return LocalDateTime(
            localDateTime.date.minus(count, DateTimeUnit.MONTH),
            localDateTime.time
        ).toAppDateTime(appLocale = localeProvider.getLocale())
    }

    override fun setHour(appDateTime: AppDateTime, hour: Int): AppDateTime {
        val localDateTime = appDateTime.toLocalDateTime()
        val localDate = localDateTime.date
        val localTime = localDateTime.time
        val newLocalTime = LocalTime(hour = hour, minute = localTime.minute)
        return LocalDateTime(localDate, newLocalTime).toAppDateTime(localeProvider.getLocale())
    }

    override fun setMinute(appDateTime: AppDateTime, minute: Int): AppDateTime {
        val localDateTime = appDateTime.toLocalDateTime()
        val localDate = localDateTime.date
        val localTime = localDateTime.time
        val newLocalTime = LocalTime(hour = localTime.hour, minute = minute)
        return LocalDateTime(
            localDate,
            newLocalTime
        ).toAppDateTime(appLocale = localeProvider.getLocale())
    }

    override fun setTime(appDateTime: AppDateTime, appTime: AppTime): AppDateTime {
        val localDateTime = appDateTime.toLocalDateTime()
        val localDate = localDateTime.date
        return LocalDateTime(
            localDate,
            appTime.toLocalTime()
        ).toAppDateTime(appLocale = localeProvider.getLocale())
    }


}