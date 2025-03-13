package at.robthered.simpletodo.features.shared.time_zone.data

import android.content.SharedPreferences
import at.robthered.simpletodo.features.shared.time_zone.domain.TimeZoneStorage
import at.robthered.simpletodo.features.shared.time_zone.domain.model.AppTimeZone
import kotlinx.datetime.TimeZone

class SharedPreferencesTimeZoneStorage (
    private val sharedPreferences: SharedPreferences
): TimeZoneStorage {
    override fun setTimeZone(appTimeZone: AppTimeZone) {
        sharedPreferences.edit()
            .putString(TIME_ZONE, appTimeZone.id)
            .apply()
    }

    override fun getTimeZone(): AppTimeZone {
        val timeZoneId = sharedPreferences.getString(TIME_ZONE, DEFAULT_TIMEZONE_ID) ?: DEFAULT_TIMEZONE_ID
        return AppTimeZone(
            id = timeZoneId
        )

    }

    companion object {
        const val TIME_ZONE = "TIME_ZONE"
        val DEFAULT_TIMEZONE_ID = TimeZone.currentSystemDefault().id
    }

}