package at.robthered.simpletodo.features.updateSectionDialog.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.presentation.components.AppCloseButton
import at.robthered.simpletodo.features.core.presentation.components.AppFilledButton
import at.robthered.simpletodo.features.core.presentation.components.AppModalBottomSheet
import at.robthered.simpletodo.features.core.presentation.components.AppModalTitle
import at.robthered.simpletodo.features.core.presentation.components.AppOutlinedTextField
import at.robthered.simpletodo.features.updateSectionDialog.domain.state.UpdateSectionStateAction
import at.robthered.simpletodo.features.updateSectionDialog.presentation.state.UpdateSectionStateHandler
import org.koin.androidx.compose.koinViewModel

@Composable
fun UpdateSectionDialogRoot(
    modifier: Modifier = Modifier,
    updateSectionViewModel: UpdateSectionViewModel = koinViewModel<UpdateSectionViewModel>(),
    onNavigateBack: () -> Unit,
) {

    val stateHandler = remember {
        UpdateSectionStateHandler(
            updateSectionViewModel = updateSectionViewModel,
            onNavigateBack = onNavigateBack
        )
    }

    UpdateSectionDialog(
        modifier = modifier,
        stateHandler = stateHandler
    )
}

@Composable
private fun UpdateSectionDialog(
    modifier: Modifier = Modifier,
    stateHandler: UpdateSectionStateHandler,
) {

    val updateSectionState by stateHandler.updateSectionState.collectAsStateWithLifecycle()
    val isLoading by stateHandler.isLoading.collectAsStateWithLifecycle()


    val listState = rememberLazyListState()

    AppModalBottomSheet(
        modifier = modifier
            .animateContentSize(),
        onDismissRequest = { stateHandler.onNavigateBack() }
    ) {
        AnimatedVisibility(
            visible = isLoading,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        AnimatedVisibility(
            visible = !isLoading,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {

            LazyColumn(
                state = listState,
                modifier = Modifier.padding(horizontal = 16.dp),
            ) {
                item {
                    AppModalTitle(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        title = stringResource(id = R.string.update_section_modal_title),
                        trailingIcon = {
                            AppCloseButton(
                                onClick = stateHandler.onNavigateBack
                            )
                        }
                    )
                }
                item {
                    AppOutlinedTextField(
                        modifier = Modifier,
                        value = updateSectionState.updateSection.title,
                        onValueChange = {
                            stateHandler.onUpdateSectionStateAction(
                                UpdateSectionStateAction.OnChangeTitle(
                                    it
                                )
                            )
                        },
                        error = updateSectionState.sectionModelErrorState.titleError,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Send
                        ),
                        keyboardActions = KeyboardActions(
                            onSend = {
                                stateHandler.onUpdateSectionStateAction(UpdateSectionStateAction.OnUpdateSection)
                            }
                        ),
                        placeholder = stringResource(id = R.string.section_title_placeholder),
                        label = stringResource(id = R.string.section_title_label),
                    )
                }

                item {
                    AppFilledButton(
                        enabled = updateSectionState.canSave,
                        onClick = {
                            stateHandler.onUpdateSectionStateAction(UpdateSectionStateAction.OnUpdateSection)
                            stateHandler.onNavigateBack()
                        },
                        text = stringResource(id = R.string.section_save_button_text)
                    )
                }
            }
        }
    }
}