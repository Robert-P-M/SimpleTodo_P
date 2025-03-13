package at.robthered.simpletodo.features.shared.date_time.domain.model

import kotlinx.serialization.Serializable

@androidx.annotation.Keep
@Serializable
enum class AppUpcomingDateEnum {
    TODAY, TOMORROW, THIS_WEEKEND, NEXT_WEEK, NEXT_WEEKEND,
}