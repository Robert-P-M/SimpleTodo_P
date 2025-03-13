package at.robthered.simpletodo.features.addTaskDialog.presentation

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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.domain.Resource
import at.robthered.simpletodo.features.core.domain.state_manager.event.EventStateManagerAction
import at.robthered.simpletodo.features.core.domain.state_manager.sharedUrls.SharedUrlsStateManagerAction
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
import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.TaskModel
import at.robthered.simpletodo.features.homeScreen.presentation.error.TaskModelError
import at.robthered.simpletodo.features.network.domain.model.SharedUrlModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddTaskDialogScreenRoot(
    modifier: Modifier = Modifier,
    addTaskDialogViewModel: AddTaskDialogViewModel = koinViewModel<AddTaskDialogViewModel>(),
    navigateTo: (route: MainRoute) -> Unit,
    onNavigateBack: () -> Unit,
) {

    val statusResource by addTaskDialogViewModel.statusResource.collectAsStateWithLifecycle()
    val taskModel by addTaskDialogViewModel.taskModel.collectAsStateWithLifecycle()
    val taskModelError by addTaskDialogViewModel.taskModelError.collectAsStateWithLifecycle()
    val priorityModel by addTaskDialogViewModel.priorityModel.collectAsStateWithLifecycle()
    val eventModel by addTaskDialogViewModel.eventModel.collectAsStateWithLifecycle()
    val sharedUrlModels by addTaskDialogViewModel.sharedUrlModels.collectAsStateWithLifecycle()
    val canSave by addTaskDialogViewModel.canSave.collectAsStateWithLifecycle()


    AddTaskDialogScreen(
        modifier = modifier,
        statusResource = statusResource,
        taskModel = taskModel,
        taskModelError = taskModelError,
        priorityModel = priorityModel,
        eventModel = eventModel,
        sharedUrlModels = sharedUrlModels,
        canSave = canSave,
        handleAction = { action: AddTaskDialogAction ->
            when (action) {
                is AddTaskDialogAction.EventAction -> Unit
                is AddTaskDialogAction.PriorityAction -> Unit
                is AddTaskDialogAction.SharedUrlAction -> Unit
                is AddTaskDialogAction.TaskAction -> Unit
                AddTaskDialogAction.OnNavigateBack -> {
                    onNavigateBack()
                }
                AddTaskDialogAction.OnNavigateToDueDialog -> {
                    navigateTo(MainRoute.DueDialog)
                }
                AddTaskDialogAction.OnNavigateToSharedUrlProcessorDialog -> navigateTo(MainRoute.SharedUrlProcessorDialog())
                AddTaskDialogAction.OnSaveTask -> Unit
                AddTaskDialogAction.OnNavigateToPriorityDialog -> navigateTo(MainRoute.PriorityPickerDialog)
            }
            addTaskDialogViewModel.handleAction(action)
        },
    )
}

@Composable
fun AddTaskDialogScreen(
    modifier: Modifier = Modifier,
    statusResource: Resource<Unit>,
    taskModel: TaskModel,
    taskModelError: TaskModelError,
    priorityModel: PriorityModel,
    eventModel: EventModel?,
    canSave: Boolean,
    sharedUrlModels: List<SharedUrlModel>,
    handleAction: (action : AddTaskDialogAction) -> Unit,
) {
    val (title, description) = remember { FocusRequester.createRefs() }

    LaunchedEffect(statusResource) {
        if(statusResource.isSuccess()) {
            handleAction(
                AddTaskDialogAction.OnNavigateBack
            )
        }
    }

    val listState = rememberLazyListState()

    val keyboardController = LocalSoftwareKeyboardController.current

    val taskLinksDialogState = rememberTaskLinksDialogState(
        initVisible = false,
        onAddItemClick = {
            handleAction(
                    AddTaskDialogAction
                        .OnNavigateToSharedUrlProcessorDialog
            )

        },
        onDismissRequest = {},
    )


    TaskLinksDialog(
        modifier = Modifier,
        tasksLinkDialogState = taskLinksDialogState,
        linksDialogItems =  {
            itemsIndexed(
                items = sharedUrlModels,
                key = { _, item -> "${item.link} ${item.hashCode()}"  }
            ) {index, item ->
                LinkDialogItem(
                    item = item,
                    index = index,
                    onDeleteItem = {
                        handleAction(
                            AddTaskDialogAction.SharedUrlAction(
                                SharedUrlsStateManagerAction
                                    .OnRemoveSharedUrlModel(
                                        link = item.link
                                    )
                            )
                        )
                    }
                )
            }
        }
    )

    AppModalBottomSheet(
        modifier = modifier.animateContentSize(),
        onDismissRequest = {
            handleAction(
                AddTaskDialogAction.OnNavigateBack
            )
        },
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {

            item {
                AppModalTitle(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    title = stringResource(id = R.string.add_task_modal_title),
                    leadingIcon = {
                        if(statusResource.isSaving()){
                            AppLoadingSpinner()
                        }
                    },
                    trailingIcon = {
                        AppCloseButton(
                            onClick = {
                                handleAction(
                                    AddTaskDialogAction.OnNavigateBack
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
                    onValueChange = {
                        handleAction(
                            AddTaskDialogAction
                                .TaskAction(
                                    TaskStateManagerAction
                                        .OnChangeTitle(
                                            it
                                        )
                                )
                        )
                    },
                    error = taskModelError.titleError,
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
                            AddTaskDialogAction
                                .TaskAction(
                                    TaskStateManagerAction
                                        .OnChangeDescription(
                                            it
                                        )
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
                    linksCount = sharedUrlModels.size,
                    onAddLinkToggle = {
                        if(sharedUrlModels.isNotEmpty()) {
                            taskLinksDialogState.onShow()
                        } else {
                            handleAction(
                                AddTaskDialogAction
                                    .OnNavigateToSharedUrlProcessorDialog
                            )
                        }
                    },
                    priorityEnum = priorityModel.priority,
                    onPriorityClick = {
                        handleAction(
                            AddTaskDialogAction
                                .OnNavigateToPriorityDialog
                        )
                    },
                    taskEventModel = eventModel,
                    onEventToggleClick = {
                        handleAction(
                            AddTaskDialogAction
                                .OnNavigateToDueDialog
                        )
                    },
                    onNotificationToggleClick = {
                        eventModel?.let { eventModel ->
                            val newEventModel = EventModel(
                                isNotificationEnabled = eventModel.isNotificationEnabled.not(),
                                start = eventModel.start,
                                isActive = eventModel.isActive,
                                durationMinutes = eventModel.durationMinutes,
                                isCompleted = eventModel.isCompleted,
                                isWithTime = eventModel.isWithTime,
                            )
                            handleAction(
                                AddTaskDialogAction
                                    .EventAction(
                                        EventStateManagerAction
                                            .OnUpdateEventModel(
                                                eventModel = newEventModel
                                            )
                                    )
                            )
                        }
                    }
                )
            }
            item {
                AppFilledButton(
                    enabled = canSave  && !statusResource.isSaving(),
                    onClick = {
                        keyboardController?.hide()
                        handleAction(
                            AddTaskDialogAction
                                .OnSaveTask
                        )
                    },
                    text = stringResource(id = R.string.task_save_button_text)
                )
            }
        }
    }
}