package at.robthered.simpletodo.features.homeScreen.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.presentation.components.AppDeleteDialog
import at.robthered.simpletodo.features.core.presentation.components.draggable.DragAnchors
import at.robthered.simpletodo.features.core.presentation.components.draggable.DraggableContainerState
import at.robthered.simpletodo.features.core.presentation.navigation.MainRoute
import at.robthered.simpletodo.features.dataSource.domain.local.model.ArchivedModel
import at.robthered.simpletodo.features.dataSource.domain.local.relation.TaskWithDetailsAndChildrenModel
import at.robthered.simpletodo.features.dataSource.presentation.model.getPriority
import at.robthered.simpletodo.features.dataSource.presentation.model.getPriorityEnum
import at.robthered.simpletodo.features.dataSource.presentation.model.isTaskArchived
import at.robthered.simpletodo.features.dataSource.presentation.model.isTaskCompleted
import at.robthered.simpletodo.features.core.domain.state_manager.archived.ArchivedStateAction
import at.robthered.simpletodo.features.homeScreen.presentation.HomeScreenAction
import at.robthered.simpletodo.features.homeScreen.presentation.components.remember.rememberDeleteDialogState
import at.robthered.simpletodo.features.homeScreen.presentation.components.remember.rememberTaskCardMenuState
import at.robthered.simpletodo.features.homeScreen.presentation.state.HomeStateHandler
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskCard(
    modifier: Modifier = Modifier,
    depth: Int,
    item: TaskWithDetailsAndChildrenModel,
    isTaskExpanded: (taskId: Long?) -> Boolean,
    stateHandler: HomeStateHandler,
) {

    val currentPriority by remember(item.priority) {
        derivedStateOf {
            item.getPriority()
        }
    }
    val isMaxDepthReached by remember(depth) {
        derivedStateOf {
            depth == 5
        }
    }

    val deleteDialogState = rememberDeleteDialogState(
        handleDelete = {
            stateHandler.handleHomeStateManagerAction(
                HomeScreenAction
                    .OnDeleteTask(taskId = item.task.taskId)
            )
        }
    )

    val taskCardMenuState = rememberTaskCardMenuState(
        taskId = item.task.taskId,
        createdAt = item.task.createdAt,
        currentPriority = currentPriority,
        onNavigateToTaskActivityLogDialog = {
            stateHandler.navigateTo(
                MainRoute.TaskActivityLogDialog(
                    taskId = item.task.taskId
                )
            )
        },
        onNavigateToUpdateTaskDialog = {
            item.task.taskId?.let {
                stateHandler.navigateTo(MainRoute.UpdateTaskDialog(it))
            }
        },
        onAddSubtaskClick = {
            stateHandler.navigateTo(
                MainRoute.AddTaskDialog(
                    parentTaskId = item.task.taskId
                )
            )
        },
        onNavigateToDetails = {
            stateHandler.navigateTo(
                MainRoute.TaskDetailsDialog(
                    taskId = item.task.taskId
                )
            )
        },
        isMaxDepthReached = isMaxDepthReached,
        onOpenPriorityPicker = {
            stateHandler.navigateTo(
                MainRoute.UpdatePriorityDialog(
                    taskId = item.task.taskId,
                    currentPriority = item.getPriorityEnum()
                )
            )
        },
        onOpenDeleteDialog = {
            deleteDialogState.onShow()
        }
    )



    val isTaskCompleted by remember(item.completed) {
        derivedStateOf { item.isTaskCompleted() }
    }
    val isTaskArchived by remember(item.archived) {
        derivedStateOf { item.isTaskArchived() }
    }


    if (deleteDialogState.isVisible) {
        AppDeleteDialog(
            deleteDialogState = deleteDialogState
        ){
            Text(
                text = stringResource(R.string.dialog_delete_task_text)
            )
        }
    }


    val coroutineScope = rememberCoroutineScope()
    var cardHeight by remember {
        mutableIntStateOf(100)
    }
    Column(
        modifier = modifier
    ) {
        SwipeableTaskCard(
            modifier = Modifier.fillMaxWidth(),
            isTaskArchived = isTaskArchived,
            onToggleIsArchived = {
                coroutineScope.launch {
                    it.dragState.animateTo(DragAnchors.RESTING)
                    stateHandler.onArchivedStateAction(
                        ArchivedStateAction.OnToggleArchived(
                            archivedModel = ArchivedModel(
                                taskId = item.task.taskId,
                                isArchived = !isTaskArchived
                            )
                        )
                    )
                }
            },
            onDeleteTask = {
                coroutineScope.launch {
                    it.dragState.animateTo(DragAnchors.RESTING)
                    deleteDialogState.onShow()
                }
            },
        ) { dragState: DraggableContainerState ->

            TaskCardHeader(
                modifier = modifier,
                dragState = dragState,
                setTaskCardHeight = { height: Int -> cardHeight = height },
                item = item,
                isTaskCompleted = isTaskCompleted,
                isTaskExpanded = isTaskExpanded,
                stateHandler = stateHandler,
                taskCardMenuState = taskCardMenuState,
            )

        }

        HorizontalDivider()
        TaskCardChildren(
            modifier = modifier,
            item = item,
            isTaskExpanded = isTaskExpanded,
            depth = depth,
            stateHandler = stateHandler
        )
    }
}