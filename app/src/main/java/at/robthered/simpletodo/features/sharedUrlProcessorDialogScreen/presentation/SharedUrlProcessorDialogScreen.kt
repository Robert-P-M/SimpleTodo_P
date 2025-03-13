package at.robthered.simpletodo.features.sharedUrlProcessorDialogScreen.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.domain.Resource
import at.robthered.simpletodo.features.core.presentation.components.AppCloseButton
import at.robthered.simpletodo.features.core.presentation.components.AppFilledButton
import at.robthered.simpletodo.features.core.presentation.components.AppModalBottomSheet
import at.robthered.simpletodo.features.core.presentation.components.AppModalTitle
import at.robthered.simpletodo.features.core.presentation.components.AppOutlinedTextField
import at.robthered.simpletodo.features.core.presentation.components.lazyColumn.lazyColumnItemResourceError
import at.robthered.simpletodo.features.core.presentation.components.lazyColumn.lazyColumnItemResourceLoading
import coil3.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel

class SharedUrlProcessorDialogStateHandler(
    private val sharedUrlProcessorDialogViewModel: SharedUrlProcessorDialogViewModel,
    private val onNavigateBack: () -> Unit,
) {

    val sharedUrlModelResource = sharedUrlProcessorDialogViewModel.sharedUrlModelResource
    val linkInput = sharedUrlProcessorDialogViewModel.linkInput
    val linkError = sharedUrlProcessorDialogViewModel.linkError
    val titleInput = sharedUrlProcessorDialogViewModel.titleInput
    val titleError = sharedUrlProcessorDialogViewModel.titleError
    val descriptionInput = sharedUrlProcessorDialogViewModel.descriptionInput
    val descriptionError = sharedUrlProcessorDialogViewModel.descriptionError
    val canSave = sharedUrlProcessorDialogViewModel.canSave
    fun handleAction(action: SharedUrlProcessorDialogAction) = sharedUrlProcessorDialogViewModel.handleAction(action)

    fun back() {
        onNavigateBack()
    }
}

@Composable
fun SharedUrlProcessorDialogScreenRoot(
    modifier: Modifier = Modifier,
    sharedUrlProcessorDialogViewModel: SharedUrlProcessorDialogViewModel = koinViewModel<SharedUrlProcessorDialogViewModel>(),
    onNavigateBack: () -> Unit,
) {
    val stateHandler = remember {
        SharedUrlProcessorDialogStateHandler(
            sharedUrlProcessorDialogViewModel = sharedUrlProcessorDialogViewModel,
            onNavigateBack = onNavigateBack
        )
    }

    SharedUrlProcessorDialogScreen(
        modifier = modifier,
        stateHandler = stateHandler
    )
}

@Composable
private fun SharedUrlProcessorDialogScreen(
    modifier: Modifier = Modifier,
    stateHandler: SharedUrlProcessorDialogStateHandler,
) {
    AppModalBottomSheet(
        modifier = modifier,
        onDismissRequest = {
            stateHandler.back()
        }
    ) {

        val sharedUrlModelResource by stateHandler.sharedUrlModelResource.collectAsStateWithLifecycle()
        val linkInput by stateHandler.linkInput.collectAsStateWithLifecycle()
        val linkError by stateHandler.linkError.collectAsStateWithLifecycle()
        val titleInput by stateHandler.titleInput.collectAsStateWithLifecycle()
        val titleError by stateHandler.titleError.collectAsStateWithLifecycle()
        val descriptionInput by stateHandler.descriptionInput.collectAsStateWithLifecycle()
        val descriptionError by stateHandler.descriptionError.collectAsStateWithLifecycle()
        val canSave by stateHandler.canSave.collectAsStateWithLifecycle()

        val listState = rememberLazyListState()
        LazyColumn(
            state = listState,
            modifier = Modifier
                .animateContentSize()
                .padding(horizontal = 16.dp),
        ) {

            item {

                AppModalTitle(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    title = stringResource(id = R.string.add_link_modal_title),
                    trailingIcon = {
                        AppCloseButton(
                            onClick = stateHandler::back
                        )
                    }
                )
            }

            item {
                AppOutlinedTextField(
                    modifier = Modifier,
                    value = linkInput,
                    onValueChange = {
                        stateHandler.handleAction(
                            SharedUrlProcessorDialogAction
                                .OnChangeUrl(it)
                        )
                    },
                    singleLine = true,
                    error = linkError,
                    keyboardOptions = KeyboardOptions.Default,
                    keyboardActions = KeyboardActions(),
                    placeholder = stringResource(id = R.string.add_link_modal_link_url_placeholder),
                    label = stringResource(id = R.string.add_link_modal_link_url_label),
                )
            }

            when(val  result = sharedUrlModelResource){
                is Resource.Stale -> Unit
                is Resource.Error -> Unit
                is Resource.Loading -> {
                    // TODO: show loading info?
                    lazyColumnItemResourceLoading()
                }
                is Resource.Success -> {
                    item {
                        AppOutlinedTextField(
                            modifier = Modifier,
                            value = titleInput,
                            onValueChange = {
                                stateHandler.handleAction(
                                    SharedUrlProcessorDialogAction
                                        .OnChangeTitle(it)
                                )
                            },
                            error = titleError,
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(),
                            placeholder = stringResource(id = R.string.add_link_modal_link_title_placeholder),
                            label = stringResource(id = R.string.add_link_modal_link_title_label)
                        )
                    }
                    item {
                        AppOutlinedTextField(
                            modifier = Modifier,
                            value = descriptionInput ?: "",
                            onValueChange = {
                                stateHandler.handleAction(
                                    SharedUrlProcessorDialogAction
                                        .OnChangeDescription(it)
                                )
                            },
                            singleLine = false,
                            error = descriptionError,
                            keyboardOptions = KeyboardOptions.Default,
                            keyboardActions = KeyboardActions(),
                            placeholder = stringResource(id = R.string.add_link_modal_link_description_placeholder),
                            label = stringResource(id = R.string.add_link_modal_link_description_label),
                        )
                    }
                    result.data.imageUrl?.let { imageUrl ->

                        item {
                            AsyncImage(
                                modifier = Modifier
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.onTertiaryContainer.copy(
                                            alpha = 0.3f
                                        ),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .clip(shape = RoundedCornerShape(8.dp))
                                    .shadow(elevation = 1.dp),
                                model = imageUrl,
                                contentDescription = null,
                            )
                        }
                    }
                }
            }
            item {
                AppFilledButton(
                    enabled = canSave,
                    onClick = {
                        stateHandler.handleAction(
                            SharedUrlProcessorDialogAction.OnSave
                        )
                        stateHandler.back()
                    },
                    text = stringResource(id = R.string.add_link_modal_save_button)
                )
            }
        }
    }
}