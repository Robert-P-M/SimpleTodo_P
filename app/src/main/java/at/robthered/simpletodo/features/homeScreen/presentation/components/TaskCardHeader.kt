package at.robthered.simpletodo.features.homeScreen.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import at.robthered.simpletodo.features.core.domain.state.expandedItemsState.ExpandedItemsStateAction
import at.robthered.simpletodo.features.core.presentation.components.AppDropdownMenu
import at.robthered.simpletodo.features.core.presentation.components.AppDropdownMenuItem
import at.robthered.simpletodo.features.core.presentation.components.AppMenuIconHorizontal
import at.robthered.simpletodo.features.core.presentation.components.AppToggleIcon
import at.robthered.simpletodo.features.core.presentation.components.draggable.DraggableContainerState
import at.robthered.simpletodo.features.core.presentation.icons.SubItemsCount
import at.robthered.simpletodo.features.core.presentation.navigation.MainRoute
import at.robthered.simpletodo.features.dataSource.presentation.model.contentColor
import at.robthered.simpletodo.features.dataSource.presentation.model.getText
import at.robthered.simpletodo.features.dataSource.domain.local.relation.TaskWithDetailsAndChildrenModel
import at.robthered.simpletodo.features.dataSource.presentation.model.backgroundColor
import at.robthered.simpletodo.features.dataSource.presentation.model.getCompletedNestedTasksCount
import at.robthered.simpletodo.features.dataSource.presentation.model.getPriorityEnum
import at.robthered.simpletodo.features.homeScreen.presentation.components.remember.TaskCardMenuState
import at.robthered.simpletodo.features.homeScreen.presentation.components.utils.priorityCardGradient
import at.robthered.simpletodo.features.homeScreen.presentation.state.HomeStateHandler
import kotlin.math.roundToInt

@Composable
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3ExpressiveApi::class)
fun TaskCardHeader(
    modifier: Modifier = Modifier,
    dragState: DraggableContainerState? = null,
    setTaskCardHeight: (height: Int) -> Unit,
    item: TaskWithDetailsAndChildrenModel,
    isTaskCompleted: Boolean,
    isTaskExpanded: (taskId: Long?) -> Boolean,
    taskCardMenuState: TaskCardMenuState,
    stateHandler: HomeStateHandler,
) {

    val priorityColor by animateColorAsState(
        targetValue = item.getPriorityEnum()
            .backgroundColor(MaterialTheme.colorScheme.surfaceVariant),
        animationSpec = tween(durationMillis = 300)
    )

    var itemWidth by remember {
        mutableIntStateOf(0)
    }

    val density = LocalDensity.current
    val itemOverscroll = ScrollableDefaults.overscrollEffect()

    Column(
        modifier = modifier
            .zIndex(1f)
            .then(
                if (dragState != null) {
                    Modifier
                        .offset {
                            IntOffset(
                                x = dragState.dragState.requireOffset().roundToInt(),
                                y = 0,
                            )
                        }
                        .anchoredDraggable(
                            state = dragState.dragState,
                            Orientation.Horizontal,
                            overscrollEffect = itemOverscroll
                        )
                } else Modifier
            )

            .onGloballyPositioned {
                setTaskCardHeight(it.size.height)
                itemWidth = it.size.width
            }
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .background(
                brush = priorityCardGradient(item.getPriorityEnum()), shape = RoundedCornerShape(4.dp)
            )
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(4.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)
            )
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {


            CompleteTaskToggleIconButton(
                taskWithDetailsAndChildrenModel = item,
                priorityColor = priorityColor,
                onCompletedStateAction = stateHandler::onCompletedStateAction,
                isTaskCompleted = isTaskCompleted
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 12.dp)
                    .clickable {
                        stateHandler.navigateTo(
                            MainRoute.TaskDetailsDialog(
                                taskId = item.task.taskId,
                            ),
                        )
                    },
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = item.task.title,
                )
                item.task.description?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                val eventModel by remember(item) {
                    derivedStateOf { item.event }
                }
                eventModel?.getText()?.let { text ->
                    Row(
                        modifier = Modifier
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(16.dp),
                            imageVector = Icons.Outlined.CalendarToday,
                            contentDescription = null,
                            tint = eventModel.contentColor()
                        )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodySmall,
                            color = eventModel.contentColor()
                        )
                        eventModel?.let {
                            if(it.isNotificationEnabled) {
                                Icon(
                                    modifier =
                                    Modifier
                                        .size(ToggleButtonDefaults.IconSize)
                                        .wrapContentSize(align = Alignment.Center),
                                    imageVector = Icons.Outlined.Alarm,
                                    contentDescription = null,
                                    tint = eventModel.contentColor()
                                )
                            }
                        }

                    }
                }
                if (item.tasks.isNotEmpty()) {
                    val completedNestedTasksCount by remember(item) {
                        derivedStateOf { item.getCompletedNestedTasksCount() }
                    }
                    Row {
                        Icon(
                            modifier = Modifier.size(16.dp),
                            imageVector = SubItemsCount,
                            contentDescription = null
                        )
                        Text(
                            text = "$completedNestedTasksCount/${item.tasks.size}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
            AppToggleIcon(
                isIconVisible = item.tasks.isNotEmpty(),
                rotateLeft = isTaskExpanded(item.task.taskId),
                onToggle = {
                    stateHandler.onExpandedTasksStateAction(
                        ExpandedItemsStateAction
                            .OnToggleItemExpanded(item.task.taskId)
                    )
                },
            )
            AppMenuIconHorizontal(onClick = { taskCardMenuState.onShow() })

            AppDropdownMenu(
                expanded = taskCardMenuState.isVisible,
                onDismissRequest = {
                    taskCardMenuState.onHide()
                },
                offset = DpOffset(x = with(density) { itemWidth.toDp() } - 32.dp,
                    y = 0.dp)
            ) {
                taskCardMenuState.menuItems.map { menuItem ->
                    AppDropdownMenuItem(
                        menuItem = menuItem
                    )
                }
            }
        }

    }
}