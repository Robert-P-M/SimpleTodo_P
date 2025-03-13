package at.robthered.simpletodo.features.shared.time_zone.data

import at.robthered.simpletodo.features.shared.time_zone.domain.model.AppTimeZone
import at.robthered.simpletodo.features.shared.time_zone.domain.AppTimeZoneProvider
import at.robthered.simpletodo.features.shared.time_zone.domain.TimeZoneStorage

class SystemAppTimeZoneProvider(
    private val timeZoneStorage: TimeZoneStorage
): AppTimeZoneProvider {
    override fun getTimeZone(): AppTimeZone {
        return timeZoneStorage.getTimeZone()
    }

    override fun setTimeZone(appTimeZone: AppTimeZone) {
        timeZoneStorage.setTimeZone(appTimeZone)
    }
}