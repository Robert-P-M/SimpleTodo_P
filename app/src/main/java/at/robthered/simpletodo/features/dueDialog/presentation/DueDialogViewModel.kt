package at.robthered.simpletodo.features.dueDialog.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import at.robthered.simpletodo.features.core.domain.eventBus.events.DueEvent
import at.robthered.simpletodo.features.core.domain.eventBus.events.DueEventBus
import at.robthered.simpletodo.features.core.domain.eventBus.on
import at.robthered.simpletodo.features.core.domain.state_manager.dueEvent.EventState
import at.robthered.simpletodo.features.core.domain.state_manager.dueEvent.DueEventStateManagerAction
import at.robthered.simpletodo.features.core.domain.state_manager.dueEvent.DueEventStateManager
import at.robthered.simpletodo.features.dataSource.domain.local.model.emptyEventModel
import at.robthered.simpletodo.features.shared.date_time.domain.AppCalendarUtils
import at.robthered.simpletodo.features.shared.date_time.domain.AppDateTimeRepository
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppCalendarMonth
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppDayOfWeek
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppUpcomingDate
import at.robthered.simpletodo.features.shared.locale.domain.AppLocaleProvider
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DueDialogViewModel(
    appCalendarUtils: AppCalendarUtils,
    appDateTimeRepository: AppDateTimeRepository,
    appLocaleProvider: AppLocaleProvider,
    private val dueEventBus: DueEventBus,
    private val dueEventStateManager: DueEventStateManager,
) : ViewModel() {

    val currentAppDateTime = appDateTimeRepository.getCurrentDateTime()
    val currentAppDate = appDateTimeRepository.getCurrentDate()
    val upcomingDays: StateFlow<List<AppUpcomingDate>> = appCalendarUtils.getUpcomingDays(currentAppDateTime = currentAppDateTime, locale = appLocaleProvider.getLocale())
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList(),
        )
    val headerDaysOfWeek: StateFlow<List<AppDayOfWeek>> = appCalendarUtils.getFormattedDaysOfWeek(appLocale = appLocaleProvider.getLocale())
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList(),
        )
    val pagedMonths: StateFlow<PagingData<AppCalendarMonth>> = appCalendarUtils.getPagedMonth()
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PagingData.empty()
        )


    val dueEventState = dueEventStateManager.state
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = EventState()
        )
    fun handleDueEventStateAction(action: DueEventStateManagerAction) = dueEventStateManager.handleAction(action)

    fun sendEventModel() {
        viewModelScope.launch {
            if(dueEventState.value.isEventActive) {
                dueEventBus.publish(
                    DueEvent.NewEventModel(
                        eventModel = dueEventState.value.eventModel.copy(isActive = true, eventId = null)
                    )
                )
            } else {
                dueEventBus.publish(
                    DueEvent.NewEventModel(eventModel = emptyEventModel)
                )
            }
        }
    }

    val dueEvents  = dueEventBus
        .on<DueEvent,DueEvent.CurrentEventModel>(
            scope = viewModelScope
        ) { event ->
        dueEventStateManager
            .handleAction(
                DueEventStateManagerAction.OnInitializeEventState(
                eventModel = event.eventModel
            )
            )
    }




}