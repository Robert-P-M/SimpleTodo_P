package at.robthered.simpletodo.features.dueDialog.presentation.components.scrollableCalendar

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.domain.state_manager.dueEvent.EventState
import at.robthered.simpletodo.features.core.domain.state_manager.dueEvent.DueEventStateManagerAction
import at.robthered.simpletodo.features.core.presentation.components.AppModalTitle
import at.robthered.simpletodo.features.core.presentation.ext.modifier.borderTop
import at.robthered.simpletodo.features.core.presentation.ext.modifier.todayItemCircle
import at.robthered.simpletodo.features.core.utils.ext.toLocalDate
import at.robthered.simpletodo.features.dueDialog.presentation.components.CalendarRow
import at.robthered.simpletodo.features.dueDialog.presentation.components.CalendarWeek
import at.robthered.simpletodo.features.dueDialog.presentation.components.MonthTitle
import at.robthered.simpletodo.features.dueDialog.presentation.components.UpcomingDateItem
import at.robthered.simpletodo.features.dueDialog.presentation.components.calendarDay.CalendarDay
import at.robthered.simpletodo.features.dueDialog.presentation.components.calendarDay.rememberCalendarDayUiModel
import at.robthered.simpletodo.features.dueDialog.presentation.state.DueDialogScreenStateHandler
import at.robthered.simpletodo.features.shared.date_time.data.mapper.toAppDate
import at.robthered.simpletodo.features.shared.date_time.data.mapper.toLocalDate
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppCalendarDayItem
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppCalendarMonth
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppCalendarWeek
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppDate
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppDateTime
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppDayOfWeek
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppUpcomingDate
import at.robthered.simpletodo.features.shared.date_time.presentation.ext.getMonthName
import at.robthered.simpletodo.features.shared.date_time.presentation.ext.toLongDayOfWeekWithDate
import at.robthered.simpletodo.features.shared.locale.data.mapper.toAppLocale
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColumnScope.ScrollableCalendar(
    modifier: Modifier = Modifier,
    stateHandler: DueDialogScreenStateHandler,
    eventState: EventState,
    upcomingDays: List<AppUpcomingDate>,
    headerDaysOfWeek: List<AppDayOfWeek>,
    pagedMonths: LazyPagingItems<AppCalendarMonth>,
    currentAppDateTime: AppDateTime,
    currentAppDate: AppDate,
) {
    val listState = rememberLazyListState()
    val flingBehaviour = rememberSnapFlingBehavior(lazyListState = listState, snapPosition = SnapPosition.Center)
    val scope = rememberCoroutineScope()


    val showScrollUp by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > (upcomingDays.size + 3)
        }
    }
    val alpha by animateFloatAsState(
        targetValue = if (showScrollUp) 1f else 0f
    )
    LazyColumn(
        modifier = modifier.weight(1f),
        state = listState,
        flingBehavior = flingBehaviour,
    ) {
        item {
            AppModalTitle(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                title = stringResource(id = R.string.due_dialog_title),
                trailingIcon = {
                    IconButton(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        onClick = { stateHandler.back() }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = null
                        )
                    }
                }
            )
        }
        item {
            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        taskEventRemoveItem(
            eventState = eventState,
            onRemoveStartClick = {
                stateHandler.handleDueEventStateAction(
                    DueEventStateManagerAction
                        .OnClearState
                )
            },
            onRemoveEndClick = {
                stateHandler.handleDueEventStateAction(
                    DueEventStateManagerAction
                        .OnSetDuration(null)
                )
            }
        )
        items(items = upcomingDays) { appUpcomingDate ->
            val event = stateHandler.dueEventState.collectAsStateWithLifecycle()
            val colorAlpha by animateFloatAsState(
                targetValue = if(appUpcomingDate.appDate == event.value.eventModel.start.toLocalDate()
                        .toAppDate(
                            appLocale = Locale.getDefault().toAppLocale()
                        ) && event.value.isEventActive) 0.2f else 0f,
                label = "appUpcomingDayBackgroundAnimation"
            )
            UpcomingDateItem(
                modifier = Modifier.background(
                    color = MaterialTheme.colorScheme.tertiary.copy(alpha = colorAlpha),
                    shape = RoundedCornerShape(size = 8.dp),
                ).border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onTertiary.copy(alpha = colorAlpha),
                    shape = RoundedCornerShape(size = 8.dp)
                ),
                appUpcomingDate = appUpcomingDate,
                onClick = {
                    stateHandler
                        .handleDueEventStateAction(
                            DueEventStateManagerAction
                                .OnSetUpcomingDate(
                                    appUpcomingDate.appDate.toLocalDate()
                                )
                        )

                }
            )
        }
        stickyHeader {
            val strokeColor = MaterialTheme.colorScheme.onSurfaceVariant
                .copy(alpha = 0.3f)
            Column(
                modifier = Modifier
                    .background(
                        BottomSheetDefaults.ContainerColor
                    )
                    .animateContentSize()
                    .borderTop(strokeColor)
                    .padding(vertical = 8.dp)
            ) {
                CalendarRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {

                    Text(
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.calendar_week_short),
                        style = MaterialTheme.typography.bodySmall
                    )
                    headerDaysOfWeek.forEach { dayOfWeek: AppDayOfWeek ->
                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .then(
                                    if (currentAppDate.isoDayNumber == dayOfWeek.isoDayNumber) {
                                        Modifier.todayItemCircle(divider = 1)
                                    } else Modifier
                                ),
                            textAlign = TextAlign.Center,
                            text = dayOfWeek.name.first().toString(),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    IconButton(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .alpha(alpha),
                        onClick = {
                            scope.launch {
                                listState.animateScrollToItem(0)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowUp,
                            contentDescription = null
                        )
                    }
                    Row(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = currentAppDate.toLongDayOfWeekWithDate(),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                HorizontalDivider(
                    thickness = 1.dp,
                    modifier = Modifier
                )
            }

        }

        items(count = pagedMonths.itemCount) { index ->
            val item = pagedMonths[index]
            if(item != null) {
                Column {

                    MonthTitle(
                        text = "${item.getMonthName().asString()} ${item.year}"
                    )

                    item.weeks.map { appCalendarWeekItems: AppCalendarWeek ->
                        CalendarRow(
                            modifier = Modifier
                                .fillMaxWidth(),
                        ) {

                            val onSetDate: (date: LocalDate, time: LocalTime) -> Unit = { date, time ->
                                stateHandler.handleDueEventStateAction(
                                    DueEventStateManagerAction.OnSetDate(
                                        LocalDateTime(
                                            date = date,
                                            time = time
                                        )
                                    )
                                )
                            }
                            appCalendarWeekItems.forEach { appCalendarDayItem: AppCalendarDayItem ->
                                when(appCalendarDayItem) {
                                    is AppCalendarDayItem.AppCalendarDay -> {
                                        val calendarDayUiModel = rememberCalendarDayUiModel(
                                            appCalendarDayItem = appCalendarDayItem,
                                            currentAppDate = currentAppDate,
                                            monthNumber = item.monthNumber,
                                            eventModel = eventState.eventModel,
                                            isEventActive = eventState.isEventActive,
                                            onSetDate =  {
                                                onSetDate(
                                                    appCalendarDayItem.day.toLocalDate(),
                                                    eventState.eventModel.start.toLocalDateTime(TimeZone.currentSystemDefault()).time
                                                )
                                            }
                                        )
                                        CalendarDay(
                                            calendarDayUiModel = calendarDayUiModel
                                        )

                                    }
                                    is AppCalendarDayItem.AppCalendarWeek -> {
                                        CalendarWeek(appCalendarDayItem = appCalendarDayItem)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}