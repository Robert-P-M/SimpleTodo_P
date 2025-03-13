package at.robthered.simpletodo.features.dueDialog.presentation


import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.AvTimer
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.domain.state_manager.dueEvent.DueEventStateManagerAction
import at.robthered.simpletodo.features.core.presentation.components.AppFilledButton
import at.robthered.simpletodo.features.core.presentation.components.AppModalBottomSheet
import at.robthered.simpletodo.features.core.presentation.ext.modifier.borderBottom
import at.robthered.simpletodo.features.core.presentation.ext.modifier.borderTop
import at.robthered.simpletodo.features.core.utils.ext.toFormattedDate
import at.robthered.simpletodo.features.core.utils.ext.toLocalTime
import at.robthered.simpletodo.features.dataSource.presentation.model.getFormattedTime
import at.robthered.simpletodo.features.dataSource.presentation.model.isDurationToggleEnabled
import at.robthered.simpletodo.features.dueDialog.presentation.components.durationPicker.DurationPickerDialog
import at.robthered.simpletodo.features.dueDialog.presentation.components.durationPicker.rememberDurationPickerState
import at.robthered.simpletodo.features.dueDialog.presentation.components.scrollableCalendar.ScrollableCalendar
import at.robthered.simpletodo.features.dueDialog.presentation.components.timePicker.TimePickerDialog
import at.robthered.simpletodo.features.dueDialog.presentation.components.timePicker.rememberTimePickerState
import at.robthered.simpletodo.features.dueDialog.presentation.state.DueDialogScreenStateHandler
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.androidx.compose.koinViewModel

@Composable
fun DueDialogScreenRoot(
    modifier: Modifier = Modifier,
    dueDialogViewModel: DueDialogViewModel = koinViewModel<DueDialogViewModel>(),
    onNavigateBack: () -> Unit,
) {

    val stateHandler = remember {
        DueDialogScreenStateHandler(
            dueDialogViewModel = dueDialogViewModel,
            onNavigateBack = onNavigateBack,
        )
    }

    DueDialogScreen(
        modifier = modifier,
        stateHandler = stateHandler
    )


}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun DueDialogScreen(
    modifier: Modifier = Modifier,
    stateHandler: DueDialogScreenStateHandler,
) {

    val eventState by stateHandler.dueEventState.collectAsStateWithLifecycle()

    val upcomingDays by stateHandler.upcomingDays.collectAsStateWithLifecycle()
    val headerDaysOfWeek by stateHandler.headerDaysOfWeek.collectAsStateWithLifecycle()
    val pagedMonths = stateHandler.pagedMonths.collectAsLazyPagingItems()
    val currentAppDateTime = stateHandler.currentAppDateTime
    val currentAppDate = stateHandler.currentAppDate

    val timePickerState = rememberTimePickerState(
        initialInstant = eventState.eventModel.start,
        onSave = { instant ->
            stateHandler.handleDueEventStateAction(
                DueEventStateManagerAction.OnEnableTime
            )
            stateHandler.handleDueEventStateAction(
                DueEventStateManagerAction.OnSetTime(time = instant.toLocalDateTime(TimeZone.currentSystemDefault()).time)
            )
        },
        onClear = {
            stateHandler.handleDueEventStateAction(
                DueEventStateManagerAction.OnDisableTime
            )
        }
    )

    TimePickerDialog(
        timePickerState = timePickerState,
    )

    val durationPickerState = rememberDurationPickerState(
        initialMinutes = eventState.eventModel.durationMinutes,
        onSave = { duration ->
            stateHandler.handleDueEventStateAction(
                DueEventStateManagerAction
                    .OnSetDuration(
                        durationMinutes = duration
                    )
            )
        },
        onClear = {
            stateHandler.handleDueEventStateAction(
                DueEventStateManagerAction
                    .OnResetDuration
            )
        }
    )

    DurationPickerDialog(
        durationPickerState = durationPickerState
    )

    AppModalBottomSheet(
        modifier = modifier,
        skipPartiallyExpanded = true,
        onDismissRequest = {
            stateHandler.back()
        }
    ) {


        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .scrollable(
                    state = scrollState,
                    orientation = Orientation.Vertical
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                ScrollableCalendar(
                    modifier = Modifier,
                    upcomingDays = upcomingDays,
                    headerDaysOfWeek = headerDaysOfWeek,
                    pagedMonths = pagedMonths,
                    currentAppDateTime = currentAppDateTime,
                    currentAppDate = currentAppDate,
                    stateHandler = stateHandler,
                    eventState = eventState,
                )
                val strokeColor = MaterialTheme.colorScheme.onSurfaceVariant
                    .copy(alpha = 0.3f)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .borderTop(strokeColor)
                        .borderBottom(strokeColor)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 16.dp,
                            alignment = Alignment.CenterHorizontally
                        ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {

                        ToggleButton(
                            modifier = Modifier.weight(1f),
                            onCheckedChange = {
                                stateHandler.handleDueEventStateAction(
                                    DueEventStateManagerAction.OnClearState
                                )
                            },
                            enabled = eventState.isEventActive,
                            checked = eventState.isEventActive,
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.CalendarToday,
                                contentDescription = null,
                                Modifier
                                    .size(ToggleButtonDefaults.IconSize)
                                    .wrapContentSize(align = Alignment.Center),
                            )
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp),
                                text = if (eventState.isEventActive) eventState.eventModel.start.toFormattedDate()
                                else stringResource(R.string.pick_a_date)
                            )
                            if (eventState.isEventActive) {
                                Icon(
                                    imageVector = Icons.Outlined.Close,
                                    contentDescription = null,
                                    modifier = Modifier.clickable {
                                        stateHandler.handleDueEventStateAction(
                                            DueEventStateManagerAction
                                                .OnClearState
                                        )
                                    }
                                )
                            }
                        }
                        ToggleButton(
                            modifier = Modifier.weight(1f),
                            checked = eventState.eventModel.isWithTime,
                            onCheckedChange = { timePickerState.onShow() },
                            enabled = eventState.isEventActive,
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.AccessTime,
                                contentDescription = null,
                                Modifier
                                    .size(ToggleButtonDefaults.IconSize)
                                    .wrapContentSize(align = Alignment.Center),
                            )
                            Text(
                                text = eventState.eventModel.getFormattedTime()?.toString()
                                    ?: stringResource(R.string.due_dialog_screen_select_time),
                                modifier = Modifier
                                    .then(
                                        if (eventState.eventModel.isWithTime) {
                                            Modifier.padding(horizontal = 8.dp)
                                        } else Modifier.padding(start = 8.dp)
                                    ),
                                textAlign = TextAlign.Start
                            )
                            if (eventState.eventModel.isWithTime) {
                                Icon(
                                    modifier = Modifier
                                        .size(ToggleButtonDefaults.IconSize)
                                        .wrapContentSize(align = Alignment.Center)
                                        .clickable {
                                            stateHandler
                                                .handleDueEventStateAction(
                                                    DueEventStateManagerAction
                                                        .OnDisableTime
                                                )
                                        },
                                    imageVector = Icons.Outlined.Close,
                                    contentDescription = null,
                                )
                            }
                        }


                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 16.dp,
                            alignment = Alignment.CenterHorizontally
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ToggleButton(
                            modifier = Modifier.weight(1f),
                            checked = eventState.eventModel.isNotificationEnabled,
                            onCheckedChange = {
                                stateHandler.handleDueEventStateAction(
                                    DueEventStateManagerAction
                                        .OnToggleNotification
                                )
                            },
                            enabled = eventState.isEventActive
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Alarm,
                                contentDescription = null,
                                modifier =
                                Modifier
                                    .size(ToggleButtonDefaults.IconSize)
                                    .wrapContentSize(align = Alignment.Center),
                            )
                            Text(
                                modifier = Modifier.padding(start = 8.dp),
                                text = stringResource(R.string.due_dialog_screen_notification)
                            )
                        }


                        ToggleButton(
                            modifier = Modifier.weight(1f),
                            checked = eventState.eventModel.isFullDay,
                            onCheckedChange = {
                                durationPickerState.onShow()

                            },
                            enabled = eventState.isEventActive && eventState.eventModel.isDurationToggleEnabled(),
                        ) {

                            Icon(
                                imageVector = Icons.Outlined.AvTimer,
                                contentDescription = null,
                                modifier =
                                Modifier
                                    .size(ToggleButtonDefaults.IconSize)
                                    .wrapContentSize(align = Alignment.Center),
                            )


                            Text(
                                modifier = Modifier
                                    .then(
                                        if (eventState.eventModel.durationMinutes != null) {
                                            Modifier.padding(horizontal = 8.dp)
                                        } else Modifier.padding(start = 8.dp)
                                    ),
                                text = eventState.eventModel.durationMinutes?.toLocalTime()
                                    ?.toString()
                                    ?: stringResource(R.string.due_dialog_screen_duration)
                            )
                            eventState.eventModel.durationMinutes?.let {
                                Icon(
                                    imageVector = Icons.Outlined.Close,
                                    contentDescription = null,
                                    modifier =
                                    Modifier
                                        .size(ToggleButtonDefaults.IconSize)
                                        .wrapContentSize(align = Alignment.Center)
                                        .clickable {
                                            stateHandler.handleDueEventStateAction(
                                                DueEventStateManagerAction
                                                    .OnResetDuration
                                            )
                                        },
                                )
                            }

                        }

                    }
                }
                AppFilledButton(
                    enabled = true,
                    onClick = {
                        stateHandler.sendEventModel()
                        stateHandler.back()
                    },
                    text = stringResource(R.string.due_dialog_save_button)
                )

            }
        }

    }
}