package at.robthered.simpletodo.features.taskActivityProtocolDialog.presentation

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.domain.Resource
import at.robthered.simpletodo.features.core.presentation.asAnnotatedString
import at.robthered.simpletodo.features.core.presentation.components.AppCloseButton
import at.robthered.simpletodo.features.core.presentation.components.AppFilledButton
import at.robthered.simpletodo.features.core.presentation.components.AppModalBottomSheet
import at.robthered.simpletodo.features.core.presentation.components.AppModalTitle
import at.robthered.simpletodo.features.core.presentation.components.lazyColumn.lazyColumnItemResourceError
import at.robthered.simpletodo.features.core.presentation.components.lazyColumn.lazyColumnItemResourceLoading
import at.robthered.simpletodo.features.taskActivityProtocolDialog.domain.model.TaskActivityModel
import at.robthered.simpletodo.features.taskActivityProtocolDialog.presentation.components.TaskActivityItem
import at.robthered.simpletodo.features.dataSource.presentation.model.activityIcon
import at.robthered.simpletodo.features.dataSource.presentation.model.activityNotificationIcon
import at.robthered.simpletodo.features.dataSource.presentation.model.activityNotificationIconTint
import at.robthered.simpletodo.features.dataSource.presentation.model.activityNotificationText
import at.robthered.simpletodo.features.dataSource.presentation.model.activityText
import at.robthered.simpletodo.features.dataSource.presentation.model.activityTint
import org.koin.androidx.compose.koinViewModel

@Composable
fun TaskActivityDialogRoot(
    modifier: Modifier = Modifier,
    taskActivityDialogViewModel: TaskActivityDialogViewModel = koinViewModel<TaskActivityDialogViewModel>(),
    onNavigateBack: () -> Unit,
) {
    val stateHandler = remember {
        TaskActivityDialogStateHandler(
            taskActivityDialogViewModel = taskActivityDialogViewModel,
            onNavigateBack = onNavigateBack
        )
    }
    TaskActivityDialog(
        modifier = modifier,
        stateHandler = stateHandler,
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TaskActivityDialog(
    modifier: Modifier = Modifier,
    stateHandler: TaskActivityDialogStateHandler,
) {

    val activities by stateHandler.activities.collectAsStateWithLifecycle()

    AppModalBottomSheet(
        modifier = Modifier.animateContentSize(),
        onDismissRequest = { stateHandler.onNavigateBack() },
    ) {

        val listState = rememberLazyListState()
        val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .animateContentSize(),
            state = listState,
        ) {
            when (val taskActivityResource = activities) {
                Resource.Stale -> Unit
                is Resource.Error -> lazyColumnItemResourceError(error = taskActivityResource.error)

                is Resource.Loading -> {
                    // TODO: show loading info?
                    lazyColumnItemResourceLoading()
                }

                is Resource.Success -> {
                    val taskActivities = taskActivityResource.data
                    item {
                        AppModalTitle(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            title = stringResource(id = R.string.task_activity_dialog_modal_title),
                            trailingIcon = {
                                AppCloseButton(
                                    onClick = stateHandler.onNavigateBack
                                )
                            }
                        )
                    }
                    items(taskActivities) { taskActivityItem: TaskActivityModel ->

                        when (taskActivityItem) {
                            is TaskActivityModel.Link -> {
                                TaskActivityItem(
                                    icon = Icons.Outlined.Image,
                                    createdAt = taskActivityItem.link.createdAt
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    )
                                    {

                                        Text(
                                            text = taskActivityItem.link.activityText().asAnnotatedString(color = MaterialTheme.colorScheme.primary)
                                        )
                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        val linkIntent = remember { Intent(Intent.ACTION_VIEW, taskActivityItem.link.linkUrl.toUri()) }
                                        AppFilledButton(
                                            text = stringResource(R.string.open_link),
                                            enabled = true,
                                            onClick = {
                                                launcher.launch(linkIntent)
                                            }
                                        )
                                    }

                                }
                            }
                            is TaskActivityModel.Task -> {
                                TaskActivityItem(
                                    icon = Icons.Outlined.Add,
                                    createdAt = taskActivityItem.task.createdAt
                                ) {
                                    Text(
                                        text = taskActivityItem.task.activityText()
                                            .asAnnotatedString(color = MaterialTheme.colorScheme.primary),
                                    )
                                }
                            }

                            is TaskActivityModel.ChildTask -> {
                                TaskActivityItem(
                                    icon = Icons.Outlined.Add,
                                    createdAt = taskActivityItem.childTask.createdAt
                                ) {
                                    Text(
                                        text = taskActivityItem.childTask.activityText(
                                            taskActivityItem.isFirst
                                        )
                                            .asAnnotatedString(color = MaterialTheme.colorScheme.primary)
                                    )
                                }
                            }

                            is TaskActivityModel.Archived -> {
                                TaskActivityItem(
                                    icon = taskActivityItem.archived.activityIcon(),
                                    createdAt = taskActivityItem.archived.createdAt ,
                                ) {
                                    Text(
                                        text = taskActivityItem.archived.activityText()
                                            .asString()
                                    )
                                }
                            }

                            is TaskActivityModel.Completed -> {
                                TaskActivityItem(
                                    icon = taskActivityItem.completed.activityIcon(),
                                    createdAt = taskActivityItem.completed.createdAt,
                                ) {
                                    Text(
                                        text = taskActivityItem.completed.activityText().asString()
                                    )
                                }
                            }

                            is TaskActivityModel.Event -> {
                                    TaskActivityItem(
                                        icon = taskActivityItem.event.activityIcon(),
                                        createdAt = taskActivityItem.event.createdAt
                                    ) {
                                        Text(
                                            text = taskActivityItem.event.activityText().asString()
                                        )
                                        taskActivityItem.event.activityNotificationText()
                                            ?.let { uiText ->
                                                Row(
                                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Icon(
                                                        modifier = Modifier
                                                            .size(ToggleButtonDefaults.IconSize)
                                                            .wrapContentSize(align = Alignment.Center),
                                                        imageVector = taskActivityItem.event.activityNotificationIcon(),
                                                        contentDescription = null,
                                                        tint = taskActivityItem.event.activityNotificationIconTint(),
                                                    )
                                                    Text(
                                                        text = uiText.asString(),
                                                        style = MaterialTheme.typography.titleSmall,
                                                        color = MaterialTheme.colorScheme.onTertiaryContainer
                                                    )
                                                }
                                            }
                                    }
                            }

                            is TaskActivityModel.Priority -> {
                                    TaskActivityItem(
                                        icon = taskActivityItem.priority.activityIcon(),
                                        iconTint = taskActivityItem.priority.activityTint(),
                                        createdAt = taskActivityItem.priority.createdAt
                                    ) {
                                        Text(
                                            text = taskActivityItem.priority.activityText().asString()
                                        )
                                    }
                            }
                        }
                    }
                }
            }
        }
    }
}