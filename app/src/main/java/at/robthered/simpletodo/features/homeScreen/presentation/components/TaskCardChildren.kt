package at.robthered.simpletodo.features.homeScreen.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.robthered.simpletodo.features.dataSource.domain.local.relation.TaskWithDetailsAndChildrenModel
import at.robthered.simpletodo.features.homeScreen.presentation.state.HomeStateHandler

@Composable
fun ColumnScope.TaskCardChildren(
    modifier: Modifier,
    item: TaskWithDetailsAndChildrenModel,
    isTaskExpanded: (taskId: Long?) -> Boolean,
    depth: Int,
    stateHandler: HomeStateHandler,
) {
    val nestedListState = rememberLazyListState()


    LazyColumn(
        modifier = modifier
            .heightIn(max = 500.dp)
            .animateContentSize()
            .padding(start = 16.dp),
        state = nestedListState
    ) {
        itemsIndexed(
            items = item.tasks,
            key = { _, item -> "nested_task_of_task-${item.task.taskId ?: item.hashCode()}" },
        ) { index: Int, subTask: TaskWithDetailsAndChildrenModel ->
            AnimatedVisibility(
                visible = isTaskExpanded(subTask.task.parentTaskId),
                enter = fadeIn(
                    animationSpec = tween(
                        delayMillis = 80 * index,
                        easing = EaseInOut
                    )
                )
            ) {

                TaskCard(
                    modifier = Modifier,
                    depth = depth + 1,
                    item = subTask,
                    isTaskExpanded = isTaskExpanded,
                    stateHandler = stateHandler,
                )

            }
        }


    }
}