package at.robthered.simpletodo.features.shared.time_zone.domain

import at.robthered.simpletodo.features.shared.time_zone.domain.model.AppTimeZone

interface AppTimeZoneProvider {
    fun getTimeZone(): AppTimeZone
    fun setTimeZone(appTimeZone: AppTimeZone)
}