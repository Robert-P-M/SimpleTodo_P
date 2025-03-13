package at.robthered.simpletodo.features.core.data.state_manager.dueEvent

import android.util.Log
import at.robthered.simpletodo.features.core.domain.state_manager.dueEvent.EventState
import at.robthered.simpletodo.features.core.domain.state_manager.dueEvent.DueEventStateManagerAction
import at.robthered.simpletodo.features.core.domain.state_manager.dueEvent.DueEventStateManager
import at.robthered.simpletodo.features.core.utils.ext.toInstant
import at.robthered.simpletodo.features.core.utils.ext.withDate
import at.robthered.simpletodo.features.core.utils.ext.withHour
import at.robthered.simpletodo.features.core.utils.ext.withMinute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DueEventStateManagerImpl: DueEventStateManager {

    private val _state: MutableStateFlow<EventState> = MutableStateFlow(EventState())
    override val state: StateFlow<EventState>
        get() = _state.asStateFlow()

    override fun handleAction(action: DueEventStateManagerAction) {
        when (action) {
            is DueEventStateManagerAction.OnSetTime -> {
                _state.update {
                    it.copy(
                        eventModel = it.eventModel.copy(
                            start = it.eventModel.start.withHour(action.time.hour).withMinute(action.time.minute)
                        )
                    )
                }
            }
            is DueEventStateManagerAction.OnSetInstant -> {
                setStateActive(true)
                onSetInstant(action)
            }
            is DueEventStateManagerAction.OnSetUpcomingDate -> {
                setStateActive(true)
                onSetUpcomingDate(action)
            }
            is DueEventStateManagerAction.OnSetDate -> {
                setStateActive(true)
                onSetDate(action)
            }
            is DueEventStateManagerAction.OnSetDuration -> {
                onSetDuration(action)
            }
            DueEventStateManagerAction.OnToggleNotification -> {
                onToggleNotification()
            }
            DueEventStateManagerAction.OnResetDuration -> {
                _state.update {
                    it.copy(
                        eventModel = it.eventModel.copy(
                            durationMinutes = null
                        )
                    )
                }
            }

            DueEventStateManagerAction.OnToggleWithTime -> onToggleWithTime()
            DueEventStateManagerAction.OnEnableTime -> onEnableTime()
            DueEventStateManagerAction.OnDisableTime -> onDisableTime()
            is DueEventStateManagerAction.OnInitializeEventState -> {
                onInitializeEventState(action)
            }
            DueEventStateManagerAction.OnClearState -> {
                setStateActive(false)
                _state.update {
                    EventState()
                }
            }
        }
    }

    private fun onSetInstant(action: DueEventStateManagerAction.OnSetInstant) {
        _state.update { currentState ->
            currentState.copy(
                eventModel = currentState.eventModel.copy(
                    start = action.instant
                )
            )
        }
    }


    private fun onDisableTime() {
        _state.update {currentState ->
            currentState.copy(
                eventModel = currentState.eventModel.copy(
                    isWithTime = false,
                    durationMinutes = null
                )
            )
        }
    }

    private fun onEnableTime() {
        _state.update {currentState ->
            currentState.copy(
                eventModel = currentState.eventModel.copy(
                    isWithTime = true
                )
            )
        }
    }

    private fun onToggleWithTime() {
        _state.update {currentState ->
            val isWithTime = currentState.eventModel.isWithTime.not()
            val durationMinutes = if(currentState.eventModel.isWithTime) currentState.eventModel.durationMinutes else null
            currentState.copy(
                eventModel = currentState.eventModel.copy(
                    isWithTime = isWithTime,
                    durationMinutes = durationMinutes
                )
            )
        }
    }

    private fun onInitializeEventState(action: DueEventStateManagerAction.OnInitializeEventState) {
        action.eventModel?.let {
            _state.update { currentState ->
                currentState.copy(
                    isEventActive = true,
                    eventModel = it
                )
            }
        }
    }

    private fun onToggleNotification() {
        _state.update { currentState ->
            currentState.copy(
                eventModel = currentState.eventModel.copy(
                    isNotificationEnabled = currentState.eventModel.isNotificationEnabled.not()
                )
            )
        }
    }



    private fun onSetUpcomingDate(action: DueEventStateManagerAction.OnSetUpcomingDate) {
        _state.update { currentState ->
            currentState.copy(
                eventModel = currentState.eventModel.copy(
                    start = action.localDate.toInstant().withHour(0).withMinute(0),
                    isWithTime = false,
                    durationMinutes = null
                )
            )
        }
    }
    private fun onSetDate(action: DueEventStateManagerAction.OnSetDate) {
        _state.update { currentState ->

            currentState.copy(
                eventModel = currentState.eventModel.copy(
                    start = currentState.eventModel.start.withDate(action.localDateTime)
                )
            )
        }
    }

    private fun setStateActive(isActive: Boolean) {
        _state.update {
            it.copy(
                isEventActive = isActive
            )
        }
    }



    private fun onSetDuration(action: DueEventStateManagerAction.OnSetDuration) {
        _state.update { currentState ->
            currentState.copy(
                eventModel = currentState.eventModel.copy(
                    durationMinutes = action.durationMinutes
                )
            )
        }
    }
}