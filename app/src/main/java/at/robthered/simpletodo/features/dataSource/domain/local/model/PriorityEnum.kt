package at.robthered.simpletodo.features.dataSource.domain.local.model

import kotlinx.serialization.Serializable

@androidx.annotation.Keep
@Serializable
enum class PriorityEnum {
    HIGH,
    NORMAL,
    LOW
}