package at.robthered.simpletodo.features.taskDetailsDialog.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.CheckCircleOutline
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.NotInterested
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.domain.Resource
import at.robthered.simpletodo.features.core.presentation.components.AppDeleteDialog
import at.robthered.simpletodo.features.core.presentation.components.AppDropdownMenu
import at.robthered.simpletodo.features.core.presentation.components.AppDropdownMenuItem
import at.robthered.simpletodo.features.core.presentation.components.AppModalBottomSheet
import at.robthered.simpletodo.features.core.presentation.components.TaskOptionsRow
import at.robthered.simpletodo.features.core.presentation.components.taskLinks.LinkDialogItem
import at.robthered.simpletodo.features.core.presentation.components.taskLinks.TaskLinksDialog
import at.robthered.simpletodo.features.core.presentation.components.taskLinks.remember.rememberTaskLinksDialogState
import at.robthered.simpletodo.features.core.presentation.icons.DescriptionIcon
import at.robthered.simpletodo.features.core.presentation.icons.SubItemsCount
import at.robthered.simpletodo.features.core.presentation.navigation.MainRoute
import at.robthered.simpletodo.features.dataSource.domain.local.model.CompletedModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.EventModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.LinkModel
import at.robthered.simpletodo.features.dataSource.domain.local.relation.TaskWithDetailsAndChildrenModel
import at.robthered.simpletodo.features.dataSource.presentation.model.areAllNestedTasksCompleted
import at.robthered.simpletodo.features.dataSource.presentation.model.getCompletedNestedTasksCount
import at.robthered.simpletodo.features.dataSource.presentation.model.getPriorityEnum
import at.robthered.simpletodo.features.dataSource.presentation.model.isTaskCompleted
import at.robthered.simpletodo.features.core.domain.state_manager.completed.CompletedStateAction
import at.robthered.simpletodo.features.core.presentation.components.AppToggleIcon
import at.robthered.simpletodo.features.homeScreen.presentation.components.remember.rememberDeleteDialogState
import at.robthered.simpletodo.features.taskDetailsDialog.presentation.components.remember.rememberTaskDetailsMenuState
import org.koin.androidx.compose.koinViewModel
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


@Composable
fun TaskDetailsDialogRoot(
    modifier: Modifier = Modifier,
    taskDetailsDialogViewModel: TaskDetailsDialogViewModel = koinViewModel<TaskDetailsDialogViewModel>(),
    navigateTo: (route: MainRoute) -> Unit,
    onNavigateBack: () -> Unit,
) {

    val taskDetailsResource by taskDetailsDialogViewModel.taskDetailsResource.collectAsStateWithLifecycle()
    val taskId = taskDetailsDialogViewModel.taskId

    val handleAction = remember(taskDetailsDialogViewModel, navigateTo, onNavigateBack) {
        { action: TaskDetailsDialogAction ->
            when (action) {
                TaskDetailsDialogAction.OnNavigateToPriorityDialog -> navigateTo(MainRoute.PriorityPickerDialog)
                is TaskDetailsDialogAction.OnRemoveLink -> Unit
                is TaskDetailsDialogAction.OnDeleteTask -> Unit
                TaskDetailsDialogAction.OnNavigateToSharedUrlProcessorDialog -> navigateTo(MainRoute.SharedUrlProcessorDialog())
                TaskDetailsDialogAction.OnNavigateBack -> onNavigateBack()
                is TaskDetailsDialogAction.OnNavigateToUpdateTaskDialog -> {
                    navigateTo(
                        MainRoute.UpdateTaskDialog(
                            taskId = action.taskId
                        )
                    )
                }
                is TaskDetailsDialogAction.OnNavigateToTaskActivityLogDialog -> {
                    navigateTo(
                        MainRoute.TaskActivityLogDialog(
                            taskId = action.taskId
                        ))
                }
                is TaskDetailsDialogAction.OnNavigateToTaskDetailsRoute -> navigateTo(
                    MainRoute.TaskDetailsDialog(
                        taskId = action.taskId
                    )
                )
                is TaskDetailsDialogAction.OnNavigateToAddTaskRoute -> {
                    navigateTo(
                        MainRoute.AddTaskDialog(
                            parentTaskId = action.parentTaskId
                        )
                    )
                }
                TaskDetailsDialogAction.OnToggleNotification -> Unit

                // TODO
                is TaskDetailsDialogAction.CompleteAction -> {

                }
                TaskDetailsDialogAction.OnNavigateToDueDialog -> {
                    navigateTo(
                        MainRoute.DueDialog
                    )
                }
            }
            taskDetailsDialogViewModel.handleAction(action)
        }
    }


    TaskDetailsDialog(
        modifier = modifier,
        taskId = taskId,
        taskDetailsResource = taskDetailsResource,
        handleAction = handleAction,
    )

}

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class, ExperimentalUuidApi::class,
)
@Composable
private fun TaskDetailsDialog(
    modifier: Modifier = Modifier,
    handleAction: (action: TaskDetailsDialogAction) -> Unit,
    taskDetailsResource: Resource<TaskWithDetailsAndChildrenModel>,
    taskId: Long?,
) {

    val sheetState = rememberModalBottomSheetState()


    val deleteDialogState = rememberDeleteDialogState(
        handleDelete = {
            (taskDetailsResource as? Resource.Success)?.let { result: Resource.Success<TaskWithDetailsAndChildrenModel> ->
                result.data.task.taskId?.let {
                    handleAction(
                        TaskDetailsDialogAction
                            .OnDeleteTask(
                                taskId = it
                            )
                    )
                    handleAction(TaskDetailsDialogAction.OnNavigateBack)
                }
            }
        },
    )
    if (deleteDialogState.isVisible) {
        AppDeleteDialog(
            deleteDialogState = deleteDialogState
        ) {
            Text(
                text = stringResource(R.string.dialog_delete_task_text)
            )
        }
    }

    /**
     * TODO:  handle task links in viewmodel not as resource - or taskLinksDialog ?
     */
    val taskLinks = remember(taskDetailsResource) {
        derivedStateOf {
            when (taskDetailsResource) {
                is Resource.Success<TaskWithDetailsAndChildrenModel> -> (taskDetailsResource as Resource.Success<TaskWithDetailsAndChildrenModel>).data.links
                else -> emptyList()
            }
        }
    }

    val taskLinksDialogState = rememberTaskLinksDialogState(
        initVisible = false,
        onAddItemClick = {
            handleAction(
                TaskDetailsDialogAction.OnNavigateToSharedUrlProcessorDialog
            )
        },
        onDismissRequest = {},
    )

    TaskLinksDialog(
        modifier = Modifier,
        tasksLinkDialogState = taskLinksDialogState,
        linksDialogItems = {
            itemsIndexed(
                items = taskLinks.value,
                key = { _, item -> "${item.linkId} ${item.hashCode()}"  }
                ) { index: Int, item: LinkModel ->
                LinkDialogItem(
                    item = item,
                    index = index,
                    onDeleteItem = {
                        handleAction(
                            TaskDetailsDialogAction
                                .OnRemoveLink(
                                    linkId = item.linkId
                                )
                        )
                    }
                )
            }
        }
    )
    var areChildrenExpanded by remember {
        mutableStateOf(false)
    }

    AppModalBottomSheet(
        modifier = Modifier.animateContentSize(),
        sheetState = sheetState,
        onDismissRequest = {
            handleAction(TaskDetailsDialogAction.OnNavigateBack)
                           },
    ) {
        val listState = rememberLazyListState()
        val showHeader by remember {
            derivedStateOf { listState.firstVisibleItemScrollOffset > 0 }
        }
        val isScrolled by remember {
            derivedStateOf { listState.firstVisibleItemIndex > 0 }
        }



        val density = LocalDensity.current

        val taskDetailsMenuState = rememberTaskDetailsMenuState(
            onNavigateToTaskActivityDialog = {
                taskId?.let {
                    handleAction(
                        TaskDetailsDialogAction
                            .OnNavigateToTaskActivityLogDialog(
                                taskId = it
                            )
                    )
                }
            },
            onOpenDeleteDialog = {
                deleteDialogState.onShow()
            },
            onOpenUpdateTaskModal = {
                taskId?.let {
                    handleAction(
                        TaskDetailsDialogAction
                            .OnNavigateToUpdateTaskDialog(
                                taskId = it
                            )
                    )
                }
            }
        )
        val menuItems = remember {
            taskDetailsMenuState.menuItems
        }

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .animateContentSize(),
            state = listState,
        ) {
            when (taskDetailsResource) {
                Resource.Stale -> Unit
                is Resource.Error -> {

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = taskDetailsResource.error.toString()
                            )
                        }
                    }
                }

                is Resource.Loading -> {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
                is Resource.Success -> {
                    val taskWithDetailsAndChildrenModel = taskDetailsResource.data



                    item {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AppDropdownMenu(
                                expanded = taskDetailsMenuState.isVisible,
                                onDismissRequest = taskDetailsMenuState::onHide,
                                offset = DpOffset(
                                    x = with(density) { taskDetailsMenuState.xOffset.toDp() } - 32.dp,
                                    y = 0.dp,
                                )
                            ) {
                                menuItems.map { menuItem ->
                                    AppDropdownMenuItem(
                                        menuItem = menuItem
                                    )
                                }
                            }
                            IconButton(
                                onClick = {}
                            ) {
                                Icon(
                                    modifier = Modifier,
                                    imageVector = Icons.Outlined.Inbox,
                                    contentDescription = null
                                )
                            }
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Inbox",
                                )
                                Icon(
                                    imageVector = Icons.Outlined.ChevronRight,
                                    contentDescription = null
                                )
                            }
                            IconButton(
                                modifier = Modifier
                                    .pointerInput(Unit) {
                                        detectTapGestures(onTap = {})
                                    },
                                onClick = {
                                    taskDetailsMenuState.onShow()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.MoreVert,
                                    contentDescription = stringResource(R.string.icon_description_menu_icon_horizontal)
                                )
                            }

                        }
                    }
                    stickyHeader {

                        val isTaskCompleted by remember(taskWithDetailsAndChildrenModel.completed) {
                            derivedStateOf { taskWithDetailsAndChildrenModel.isTaskCompleted() }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .onGloballyPositioned {
                                    taskDetailsMenuState.updateRowEnd(it.size.width)
                                    taskDetailsMenuState.updateRowTop(it.size.height)
                                }
                                .animateContentSize()
                                    then (
                                    if (showHeader) {
                                        Modifier
                                            .background(
                                                BottomSheetDefaults.ContainerColor
                                            )
                                            .padding(vertical = 8.dp)
                                    } else Modifier
                                    ),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                modifier = Modifier.pointerInput(Unit) {
                                    detectTapGestures(onTap = {})
                                }, enabled = true, colors = IconButtonDefaults.iconButtonColors(
                                    disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(
                                        alpha = 0.5f
                                    )
                                ), onClick = {

                                    handleAction(
                                        TaskDetailsDialogAction.CompleteAction(
                                            CompletedStateAction.OnToggleCompletion(
                                                completedModel = CompletedModel(
                                                    taskId = taskWithDetailsAndChildrenModel.task.taskId,
                                                    isCompleted = !isTaskCompleted
                                                )
                                            )
                                        )

                                    )
                                }) {
                                val canCompleteTask by remember(taskWithDetailsAndChildrenModel) {
                                    derivedStateOf { taskWithDetailsAndChildrenModel.areAllNestedTasksCompleted() }
                                }
                                Icon(
                                    imageVector = if (canCompleteTask) {
                                        if (isTaskCompleted) Icons.Outlined.CheckCircleOutline else Icons.Outlined.Circle
                                    } else {
                                        Icons.Outlined.NotInterested
                                    },
                                    contentDescription = null
                                )
                            }
                            Text(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        taskWithDetailsAndChildrenModel.task.taskId?.let { taskId ->
                                            handleAction(
                                                TaskDetailsDialogAction
                                                    .OnNavigateToUpdateTaskDialog(
                                                        taskId = taskId
                                                    )
                                            )
                                        }
                                    },
                                text = taskWithDetailsAndChildrenModel.task.title,
                                textAlign = TextAlign.Start
                            )
                            AnimatedVisibility(
                                visible = isScrolled
                            ) {
                                IconButton(
                                    modifier = Modifier
                                        .pointerInput(Unit) {
                                            detectTapGestures(onTap = {})
                                        },
                                    onClick = {
                                        taskDetailsMenuState.onShow()
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.MoreVert,
                                        contentDescription = stringResource(R.string.icon_description_menu_icon_horizontal)
                                    )
                                }
                            }
                        }
                    }

                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {

                                    taskWithDetailsAndChildrenModel.task.taskId?.let { taskId ->
                                        handleAction(
                                            TaskDetailsDialogAction
                                                .OnNavigateToUpdateTaskDialog(
                                                    taskId = taskId
                                                )
                                        )
                                    }
                                },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            IconButton(
                                modifier = Modifier.pointerInput(Unit) {
                                    detectTapGestures(onTap = {})
                                }, enabled = true, colors = IconButtonDefaults.iconButtonColors(
                                    disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(
                                        alpha = 0.5f
                                    )
                                ), onClick = {

                                }
                            ) {
                                Icon(
                                    imageVector = DescriptionIcon,
                                    contentDescription = null
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .padding(vertical = 16.dp)
                                    .weight(1f),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Top
                            ) {
                                if (taskWithDetailsAndChildrenModel.task.description.isNullOrBlank()) {
                                    Text(
                                        text = stringResource(R.string.description_not_set)
                                    )
                                } else {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = taskWithDetailsAndChildrenModel.task.description,
                                    )
                                }
                            }
                        }
                    }

                    item {

                        val priorityEnum by remember(taskWithDetailsAndChildrenModel) {
                            derivedStateOf {
                                taskWithDetailsAndChildrenModel.getPriorityEnum()
                            }
                        }
                        val taskEventModel: EventModel? by remember(taskWithDetailsAndChildrenModel) {
                            derivedStateOf { taskWithDetailsAndChildrenModel.event }
                        }
                        TaskOptionsRow(
                            linksCount = taskWithDetailsAndChildrenModel.links.size,
                            onAddLinkToggle = {
                                taskLinksDialogState.onShow()
                            },
                            priorityEnum = priorityEnum,
                            onPriorityClick = {
                                handleAction(
                                    TaskDetailsDialogAction.OnNavigateToPriorityDialog
                                )
                            },
                            taskEventModel = taskEventModel,
                            onEventToggleClick = {
                                handleAction(
                                    TaskDetailsDialogAction.OnNavigateToDueDialog
                                )
                            },
                            onNotificationToggleClick = {
                                taskEventModel?.let { eventModel ->
                                    val newEventModel = EventModel(
                                        taskId = eventModel.taskId,
                                        isNotificationEnabled = eventModel.isNotificationEnabled.not(),
                                        start = eventModel.start,
                                        isActive = eventModel.isActive,
                                        durationMinutes = eventModel.durationMinutes,
                                        isCompleted = eventModel.isCompleted,
                                        isWithTime = eventModel.isWithTime,                               )


                                    handleAction(
                                        TaskDetailsDialogAction
                                            .OnToggleNotification
                                    )
                                }
                            }
                        )
                    }

                    item {
                        HorizontalDivider(
                            thickness = 6.dp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    if (taskWithDetailsAndChildrenModel.tasks.isNotEmpty()) {
                        item {
                            val completedNestedTasksCount by remember(
                                taskWithDetailsAndChildrenModel
                            ) {
                                derivedStateOf { taskWithDetailsAndChildrenModel.getCompletedNestedTasksCount() }
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    modifier = Modifier,
                                    text = "Subtasks $completedNestedTasksCount/${taskWithDetailsAndChildrenModel.tasks.size}",
                                )
                                AppToggleIcon(
                                    isIconVisible = taskWithDetailsAndChildrenModel.tasks.isNotEmpty(),
                                    rotateLeft = areChildrenExpanded,
                                    onToggle = {
                                        areChildrenExpanded = areChildrenExpanded.not()
                                    }
                                )
                            }
                        }
                    }
                    if (areChildrenExpanded) {
                        items(
                            items = taskWithDetailsAndChildrenModel.tasks,
                            key = { item -> item.task.taskId ?: Uuid.random().toString() }
                        ) { subTask: TaskWithDetailsAndChildrenModel ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        subTask.task.taskId?.let {

                                            handleAction(
                                                TaskDetailsDialogAction
                                                    .OnNavigateToTaskDetailsRoute(
                                                        taskId = it
                                                    )
                                            )
                                        }
                                    },
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val completedNestedTasksCount by remember(subTask) {
                                    derivedStateOf { subTask.getCompletedNestedTasksCount() }
                                }
                                val canCompleteTask by remember(subTask) {
                                    derivedStateOf { subTask.areAllNestedTasksCompleted() }
                                }
                                val isSubTaskCompleted by remember(subTask.completed) {
                                    derivedStateOf { subTask.isTaskCompleted() }
                                }
                                IconButton(
                                    modifier = Modifier.pointerInput(Unit) {
                                        detectTapGestures(onTap = {})
                                    }, enabled = true, colors = IconButtonDefaults.iconButtonColors(
                                        disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(
                                            alpha = 0.5f
                                        )
                                    ), onClick = {
                                        handleAction(
                                            TaskDetailsDialogAction
                                                .CompleteAction(
                                                    CompletedStateAction.OnToggleCompletion(
                                                        completedModel = CompletedModel(
                                                            taskId = subTask.task.taskId,
                                                            isCompleted = !isSubTaskCompleted
                                                        )
                                                    )
                                                )
                                        )
                                    }) {

                                    Icon(
                                        imageVector = if (canCompleteTask) {
                                            if (isSubTaskCompleted) Icons.Outlined.CheckCircleOutline else Icons.Outlined.Circle
                                        } else {
                                            Icons.Outlined.NotInterested
                                        },
                                        contentDescription = null
                                    )
                                }
                                Column(
                                    modifier = Modifier.weight(1f),
                                ) {

                                    Text(
                                        text = subTask.task.title,
                                        textAlign = TextAlign.Start
                                    )
                                    if (subTask.tasks.isNotEmpty()) {
                                        Row {
                                            Icon(
                                                modifier = Modifier.size(16.dp),
                                                imageVector = SubItemsCount,
                                                contentDescription = null
                                            )
                                            Text(
                                                text = "$completedNestedTasksCount/${subTask.tasks.size}",
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    taskWithDetailsAndChildrenModel.task.taskId?.let {

                                        handleAction(
                                            TaskDetailsDialogAction.OnNavigateToAddTaskRoute(
                                                parentTaskId = it
                                            )
                                        )
                                    }
                                },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(modifier = Modifier, onClick = {

                            }) {
                                Icon(
                                    imageVector = Icons.Outlined.Add,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(R.string.task_detail_dialog_add_sub_task),
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }

                }
            }

        }


    }


}