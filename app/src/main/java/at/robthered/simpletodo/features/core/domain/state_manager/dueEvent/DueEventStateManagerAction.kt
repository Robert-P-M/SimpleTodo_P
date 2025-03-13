package at.robthered.simpletodo.features.core.domain.state_manager.dueEvent

import at.robthered.simpletodo.features.dataSource.domain.local.model.EventModel
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

sealed interface DueEventStateManagerAction {
    data class OnSetInstant(val instant: Instant): DueEventStateManagerAction
    data class OnSetUpcomingDate(val localDate: LocalDate): DueEventStateManagerAction
    data class OnSetDate(val localDateTime: LocalDateTime): DueEventStateManagerAction
    data class OnSetTime(val time: LocalTime): DueEventStateManagerAction
    data class OnSetDuration(val durationMinutes: Int?): DueEventStateManagerAction
    data object OnResetDuration: DueEventStateManagerAction
    data object OnToggleNotification: DueEventStateManagerAction
    data object OnToggleWithTime: DueEventStateManagerAction
    data object OnEnableTime: DueEventStateManagerAction
    data object OnDisableTime: DueEventStateManagerAction
    data object OnClearState: DueEventStateManagerAction
    data class OnInitializeEventState(val eventModel: EventModel?): DueEventStateManagerAction
}