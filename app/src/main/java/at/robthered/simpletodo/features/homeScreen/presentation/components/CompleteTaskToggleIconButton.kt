package at.robthered.simpletodo.features.homeScreen.presentation.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircleOutline
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.NotInterested
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import at.robthered.simpletodo.features.dataSource.domain.local.model.CompletedModel
import at.robthered.simpletodo.features.dataSource.domain.local.relation.TaskWithDetailsAndChildrenModel
import at.robthered.simpletodo.features.dataSource.presentation.model.areAllNestedTasksCompleted
import at.robthered.simpletodo.features.core.domain.state_manager.completed.CompletedStateAction

@Composable
fun CompleteTaskToggleIconButton(
    taskWithDetailsAndChildrenModel: TaskWithDetailsAndChildrenModel,
    priorityColor: Color,
    onCompletedStateAction: (action: CompletedStateAction) -> Unit,
    isTaskCompleted: Boolean,
) {
    val canCompleteTask by remember(taskWithDetailsAndChildrenModel) {
        derivedStateOf { taskWithDetailsAndChildrenModel.areAllNestedTasksCompleted() }
    }

    IconButton(modifier = Modifier.pointerInput(Unit) {
        detectTapGestures(onTap = {})
    }, enabled = canCompleteTask, colors = IconButtonDefaults.iconButtonColors(
        contentColor = priorityColor,
        disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
    ), onClick = {
        onCompletedStateAction(
            CompletedStateAction.OnToggleCompletion(
                completedModel = CompletedModel(
                    taskId = taskWithDetailsAndChildrenModel.task.taskId, isCompleted = !isTaskCompleted
                )
            )
        )
    }) {
        Icon(
            imageVector = if (canCompleteTask) {
                if (isTaskCompleted) Icons.Outlined.CheckCircleOutline else Icons.Outlined.Circle
            } else {
                Icons.Outlined.NotInterested
            }, contentDescription = null
        )
    }
}