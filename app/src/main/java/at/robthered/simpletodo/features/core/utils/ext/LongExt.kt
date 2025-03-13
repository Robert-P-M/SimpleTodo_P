package at.robthered.simpletodo.features.core.utils.ext

import kotlinx.datetime.Instant

fun Long.toInstant(): Instant {
    return Instant.fromEpochMilliseconds(this)
}