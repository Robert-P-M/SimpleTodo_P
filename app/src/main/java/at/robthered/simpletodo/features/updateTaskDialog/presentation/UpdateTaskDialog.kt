package at.robthered.simpletodo.features.updateTaskDialog.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.domain.Resource
import at.robthered.simpletodo.features.core.domain.state_manager.task.TaskStateManagerAction
import at.robthered.simpletodo.features.core.presentation.components.AppCloseButton
import at.robthered.simpletodo.features.core.presentation.components.AppFilledButton
import at.robthered.simpletodo.features.core.presentation.components.AppLoadingSpinner
import at.robthered.simpletodo.features.core.presentation.components.AppModalBottomSheet
import at.robthered.simpletodo.features.core.presentation.components.AppModalTitle
import at.robthered.simpletodo.features.core.presentation.components.AppOutlinedTextField
import at.robthered.simpletodo.features.core.presentation.components.TaskOptionsRow
import at.robthered.simpletodo.features.core.presentation.components.taskLinks.LinkDialogItem
import at.robthered.simpletodo.features.core.presentation.components.taskLinks.TaskLinksDialog
import at.robthered.simpletodo.features.core.presentation.components.taskLinks.remember.rememberTaskLinksDialogState
import at.robthered.simpletodo.features.core.presentation.ext.isSaving
import at.robthered.simpletodo.features.core.presentation.ext.isSuccess
import at.robthered.simpletodo.features.core.presentation.ext.loadingInfo
import at.robthered.simpletodo.features.core.presentation.navigation.MainRoute
import at.robthered.simpletodo.features.dataSource.domain.local.model.EventModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.LinkModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.TaskModel
import at.robthered.simpletodo.features.dataSource.domain.local.relation.TaskWithDetailsAndChildrenModel
import at.robthered.simpletodo.features.homeScreen.presentation.error.TaskModelError
import at.robthered.simpletodo.features.network.domain.model.SharedUrlModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun UpdateTaskDialogRoot(
    modifier: Modifier = Modifier,
    updateTaskDialogViewModel: UpdateTaskDialogViewModel = koinViewModel<UpdateTaskDialogViewModel>(),
    navigateTo: (route: MainRoute) -> Unit,
    onNavigateBack: () -> Unit,
) {


    val loadingResource by updateTaskDialogViewModel.loadingResource.collectAsStateWithLifecycle()

    val taskModel by updateTaskDialogViewModel.taskModel.collectAsStateWithLifecycle()
    val taskModelError by updateTaskDialogViewModel.taskModelError.collectAsStateWithLifecycle()
    val canSave by updateTaskDialogViewModel.canSave.collectAsStateWithLifecycle()
    val priorityModel by updateTaskDialogViewModel.priorityModel.collectAsStateWithLifecycle()
    val eventModel by updateTaskDialogViewModel.eventModel.collectAsStateWithLifecycle()
    val links by updateTaskDialogViewModel.links.collectAsStateWithLifecycle()
    val newLinks by updateTaskDialogViewModel.newLinks.collectAsStateWithLifecycle()

    val statusResource by updateTaskDialogViewModel.statusResource.collectAsStateWithLifecycle()




    UpdateTaskDialog(
        modifier = modifier,
        loadingResource = loadingResource,
        statusResource = statusResource,
        taskModel = taskModel,
        taskModelError = taskModelError,
        canSave = canSave,
        priorityModel = priorityModel,
        eventModel = eventModel,
        links = links,
        newLinks = newLinks,
        handleAction = {action: UpdateTaskDialogAction ->
            when (action) {
                UpdateTaskDialogAction.OnNavigateBack -> onNavigateBack()
                UpdateTaskDialogAction.OnNavigateToSharedUrlProcessorDialog -> navigateTo(MainRoute.SharedUrlProcessorDialog())
                UpdateTaskDialogAction.OnNavigateToPriorityDialog -> {
                    navigateTo(
                        MainRoute.PriorityPickerDialog
                    )
                }
                UpdateTaskDialogAction.OnToggleNotification -> Unit
                UpdateTaskDialogAction.OnNavigateToDueDialog -> {
                    navigateTo(
                        MainRoute.DueDialog
                    )
                }
                UpdateTaskDialogAction.OnUpdateTask -> Unit
                is UpdateTaskDialogAction.OnRemoveLink -> Unit
                is UpdateTaskDialogAction.TaskAction -> Unit
                is UpdateTaskDialogAction.EventAction -> Unit
                is UpdateTaskDialogAction.PriorityAction -> Unit
            }
            updateTaskDialogViewModel.handleAction(action)
        },

    )

}

@Composable
private fun UpdateTaskDialog(
    modifier: Modifier = Modifier,
    handleAction: (action: UpdateTaskDialogAction) -> Unit,
    taskModel: TaskModel,
    taskModelError: TaskModelError,
    canSave: Boolean,
    priorityModel: PriorityModel,
    eventModel: EventModel?,
    links: List<LinkModel>,
    newLinks: List<SharedUrlModel>,
    statusResource: Resource<Unit>,
    loadingResource: Resource<TaskWithDetailsAndChildrenModel>,
) {
    val (title, description) = remember { FocusRequester.createRefs() }

    LaunchedEffect(statusResource) {
        if(statusResource.isSuccess()) {
            handleAction(
                UpdateTaskDialogAction.OnNavigateBack
            )
        }
    }

    val listState = rememberLazyListState()

    val taskLinksDialogState = rememberTaskLinksDialogState(
        initVisible = false,
        onAddItemClick = {
            handleAction(
                UpdateTaskDialogAction
                    .OnNavigateToSharedUrlProcessorDialog
            )
        },
        onDismissRequest = {},
    )

    TaskLinksDialog(
        modifier = Modifier,
        tasksLinkDialogState = taskLinksDialogState,
        linksDialogItems = {
            itemsIndexed(
                items = links,
                key = { _, item -> "${item.linkId} ${item.hashCode()}"  }
            ) { index: Int, item: LinkModel ->
                LinkDialogItem(
                    item = item,
                    index = index,
                    onDeleteItem = {linkId ->
                        handleAction(
                            UpdateTaskDialogAction
                                .OnRemoveLink(
                                    linkId = linkId
                                )
                        )
                    }
                )
            }
            itemsIndexed(
                items = newLinks,
                key = { _, item -> "${item.link} - ${item.hashCode()}"}
            ) {index: Int, item: SharedUrlModel ->
                LinkDialogItem(
                    item = item,
                    new = true,
                    index = index,
                    onDeleteItem = {}
                )
            }
        }
    )

    AppModalBottomSheet(
        modifier = modifier
            .animateContentSize(),
        onDismissRequest = {
            handleAction(
                UpdateTaskDialogAction
                    .OnNavigateBack
            )
        },
    ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .padding(horizontal = 16.dp),
            ) {
                item {
                    AppModalTitle(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        title = stringResource(id = R.string.update_task_modal_title),
                        leadingIcon = {
                            if(statusResource.isSaving()){
                                AppLoadingSpinner()
                            }
                        },
                        trailingIcon = {
                            AppCloseButton(
                                onClick = {
                                    handleAction(
                                        UpdateTaskDialogAction
                                            .OnNavigateBack
                                    )
                                }
                            )
                        }
                    )
                }
                statusResource.loadingInfo()?.let { text ->
                    item {
                        Row(
                            modifier = Modifier
                                .animateItem(
                                    fadeInSpec = tween(),
                                    fadeOutSpec = tween(),
                                    placementSpec = tween(),
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = text.asString()
                            )
                        }
                    }
                }
                item {
                    AppOutlinedTextField(
                        modifier = Modifier
                            .focusRequester(title),
                        value = taskModel.title,
                        error = taskModelError.titleError,
                        onValueChange = {
                            handleAction(
                                UpdateTaskDialogAction
                                    .TaskAction(
                                        TaskStateManagerAction
                                            .OnChangeTitle(it)
                                    )
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                description.requestFocus()
                            }
                        ),
                        placeholder = stringResource(id = R.string.task_title_placeholder),
                        label = stringResource(R.string.task_title_label)
                    )
                }
                item {
                    AppOutlinedTextField(
                        modifier = Modifier
                            .focusRequester(description),
                        value = taskModel.description ?: "",
                        onValueChange = {
                            handleAction(
                                UpdateTaskDialogAction
                                    .TaskAction(
                                        TaskStateManagerAction
                                            .OnChangeDescription(it)
                                    )
                            )
                        },
                        singleLine = false,
                        error = taskModelError.descriptionError,
                        keyboardOptions = KeyboardOptions.Default,
                        keyboardActions = KeyboardActions(),
                        placeholder = stringResource(id = R.string.task_description_placeholder),
                        label = stringResource(id = R.string.task_description_label),
                    )


                }
                item {
                    TaskOptionsRow(
                        linksCount = links.size + newLinks.size,
                        onAddLinkToggle = {
                            taskLinksDialogState.onShow()
                        },
                        priorityEnum = priorityModel.priority,
                        onPriorityClick = {
                            handleAction(
                                UpdateTaskDialogAction
                                    .OnNavigateToPriorityDialog
                            )
                        },
                        taskEventModel = eventModel,
                        onEventToggleClick = {
                            handleAction(
                                UpdateTaskDialogAction
                                    .OnNavigateToDueDialog
                            )
                        },
                        onNotificationToggleClick = {
                            handleAction(
                                UpdateTaskDialogAction
                                    .OnToggleNotification
                            )
                        }
                    )
                }
                item {
                    AppFilledButton(
                        enabled = canSave  && !statusResource.isSaving(),
                        onClick = {
                            handleAction(
                                UpdateTaskDialogAction
                                    .OnUpdateTask
                            )
                        },
                        text = stringResource(id = R.string.task_update_button_text)
                    )
                }
            }


    }

}