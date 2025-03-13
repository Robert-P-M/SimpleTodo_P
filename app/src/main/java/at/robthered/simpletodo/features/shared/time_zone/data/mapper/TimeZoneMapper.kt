package at.robthered.simpletodo.features.shared.time_zone.data.mapper

import at.robthered.simpletodo.features.shared.time_zone.domain.model.AppTimeZone
import kotlinx.datetime.TimeZone

fun TimeZone.toAppTimeZone(): AppTimeZone {
    return AppTimeZone(this.id)
}