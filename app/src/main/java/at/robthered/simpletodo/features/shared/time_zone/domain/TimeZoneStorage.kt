package at.robthered.simpletodo.features.shared.time_zone.domain

import at.robthered.simpletodo.features.shared.time_zone.domain.model.AppTimeZone

interface TimeZoneStorage {
    fun setTimeZone(appTimeZone: AppTimeZone)
    fun getTimeZone(): AppTimeZone
}