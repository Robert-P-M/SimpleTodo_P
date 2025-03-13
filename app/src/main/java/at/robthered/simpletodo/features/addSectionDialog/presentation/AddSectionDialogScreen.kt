package at.robthered.simpletodo.features.addSectionDialog.presentation

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.domain.Resource
import at.robthered.simpletodo.features.core.domain.state_manager.section.SectionStateAction
import at.robthered.simpletodo.features.core.presentation.components.AppCloseButton
import at.robthered.simpletodo.features.core.presentation.components.AppFilledButton
import at.robthered.simpletodo.features.core.presentation.components.AppLoadingSpinner
import at.robthered.simpletodo.features.core.presentation.components.AppModalBottomSheet
import at.robthered.simpletodo.features.core.presentation.components.AppModalTitle
import at.robthered.simpletodo.features.core.presentation.components.AppOutlinedTextField
import at.robthered.simpletodo.features.core.presentation.ext.isSaving
import at.robthered.simpletodo.features.core.presentation.ext.isSuccess
import at.robthered.simpletodo.features.core.presentation.ext.loadingInfo
import at.robthered.simpletodo.features.dataSource.domain.local.model.SectionModel
import at.robthered.simpletodo.features.homeScreen.presentation.error.SectionModelError
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddSectionDialogScreenRoot(
    modifier: Modifier,
    addSectionDialogViewModel: AddSectionDialogViewModel = koinViewModel<AddSectionDialogViewModel>(),
    onNavigateBack: () -> Unit,
) {

    val statusResource by addSectionDialogViewModel.statusResource.collectAsStateWithLifecycle()
    val sectionModel by addSectionDialogViewModel.sectionModel.collectAsStateWithLifecycle()
    val sectionModelError by addSectionDialogViewModel.sectionModelError.collectAsStateWithLifecycle()
    val canSave by addSectionDialogViewModel.canSave.collectAsStateWithLifecycle()

    AddSectionDialogScreen(
        modifier = modifier,
        statusResource = statusResource,
        sectionModel = sectionModel,
        sectionModelError = sectionModelError,
        canSave = canSave,
        handleAction = { action: AddSectionDialogScreenAction ->
            when (action) {
                AddSectionDialogScreenAction.OnNavigateBack -> {
                    onNavigateBack()
                }
                else -> Unit
            }
            addSectionDialogViewModel.handleAddSectionDialogAction(action = action)
                       },
    )

}

@Composable
private fun AddSectionDialogScreen(
    modifier: Modifier = Modifier,
    statusResource: Resource<Unit>,
    sectionModel: SectionModel,
    sectionModelError: SectionModelError,
    canSave: Boolean,
    handleAction: (action: AddSectionDialogScreenAction) -> Unit,
) {

    val focusRequester = remember {
        FocusRequester()
    }


    LaunchedEffect(statusResource) {
        if(statusResource.isSuccess()) {
            handleAction(
                AddSectionDialogScreenAction
                    .OnNavigateBack
            )
        }
    }


    val keyboardController = LocalSoftwareKeyboardController.current



    AppModalBottomSheet(
        modifier = modifier,
        onDismissRequest = {
            handleAction(
                AddSectionDialogScreenAction
                    .OnSaveSection
            )
        }
    ) {

        val listState = rememberLazyListState()
        LazyColumn(
            state = listState,
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            item {
                AppModalTitle(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    title = stringResource(id = R.string.add_section_modal_title),
                    leadingIcon = {
                        if(statusResource.isSaving()){
                            AppLoadingSpinner()
                        }
                    },
                    trailingIcon = {
                        AppCloseButton(
                            onClick = {
                                handleAction(
                                    AddSectionDialogScreenAction
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
                        .focusRequester(focusRequester),
                    value = sectionModel.title,
                    onValueChange = {
                        handleAction(
                            AddSectionDialogScreenAction
                                .SectionAction(
                                    SectionStateAction
                                        .OnChangeTitle(it)
                                )
                        )
                    },
                    error = sectionModelError.titleError,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Send
                    ),
                    keyboardActions = KeyboardActions(
                        onSend = {
                            keyboardController?.hide()
                            handleAction(
                                AddSectionDialogScreenAction
                                    .OnSaveSection
                            )
                        }
                    ),
                    placeholder = stringResource(id = R.string.section_title_placeholder),
                    label = stringResource(id = R.string.section_title_label),
                )
            }

            item {
                AppFilledButton(
                    enabled = canSave && !statusResource.isSaving(),
                    onClick = {
                        keyboardController?.hide()
                        handleAction(
                            AddSectionDialogScreenAction
                                .OnSaveSection
                        )
                    },
                    text = stringResource(id = R.string.section_save_button_text)
                )
            }
        }

    }
}