package at.robthered.simpletodo.features.core.data.state_manager.event

import android.util.Log
import at.robthered.simpletodo.features.core.domain.eventBus.clear
import at.robthered.simpletodo.features.core.domain.eventBus.events.DueEvent
import at.robthered.simpletodo.features.core.domain.eventBus.events.DueEventBus
import at.robthered.simpletodo.features.core.domain.eventBus.on
import at.robthered.simpletodo.features.core.domain.state_manager.event.EventStateManager
import at.robthered.simpletodo.features.core.domain.state_manager.event.EventStateManagerAction
import at.robthered.simpletodo.features.dataSource.domain.local.model.EventModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EventStateManagerImpl(
    private val dueEventBus: DueEventBus,
    private val coroutineScope: CoroutineScope,
): EventStateManager {
    private val _eventModel: MutableStateFlow<EventModel?> = MutableStateFlow(null)
    override val eventModel: StateFlow<EventModel?>
        get() = _eventModel.asStateFlow()

    override fun handleAction(action: EventStateManagerAction) {
        when (action) {
            is EventStateManagerAction.OnInitializeState -> onInitializeState(action)
            EventStateManagerAction.OnResetEvent -> onResetEvent()
            is EventStateManagerAction.OnUpdateEventModel -> onUpdateEventModel(action)
            EventStateManagerAction.OnSendCurrentEvent -> {
                coroutineScope.launch {
                    dueEventBus.publish(
                        DueEvent.CurrentEventModel(_eventModel.value)
                    )
                }
            }
        }
    }
    private val dueEvents = dueEventBus
        .on<DueEvent, DueEvent.NewEventModel>(scope = coroutineScope) { event ->
            _eventModel.update { event.eventModel.copy(eventId = null) }
        }

    private fun onUpdateEventModel(action: EventStateManagerAction.OnUpdateEventModel) {
        _eventModel.value = action.eventModel
    }

    private fun onResetEvent() {
        _eventModel.value = EventModel()
        coroutineScope.launch {
            dueEventBus.clear(DueEvent.ClearEvent)
        }
    }

    private fun onInitializeState(action: EventStateManagerAction.OnInitializeState) {
        _eventModel.update { action.eventModel }
    }
}