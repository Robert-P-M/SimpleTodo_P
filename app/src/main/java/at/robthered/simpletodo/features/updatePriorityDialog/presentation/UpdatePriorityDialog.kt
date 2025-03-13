package at.robthered.simpletodo.features.updatePriorityDialog.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import at.robthered.simpletodo.features.core.presentation.components.priorityPicker.PriorityPickerDialog
import at.robthered.simpletodo.features.core.presentation.components.priorityPicker.rememberPriorityPickerState
import at.robthered.simpletodo.features.updatePriorityDialog.presentation.state.UpdatePriorityDialogStateHandler
import org.koin.androidx.compose.koinViewModel

// TODO: priorityEventBus ??

@Composable
fun UpdatePriorityDialogRoot(
    modifier: Modifier = Modifier,
    updatePriorityDialogViewModel: UpdatePriorityDialogViewModel = koinViewModel<UpdatePriorityDialogViewModel>(),
    onNavigateBack: () -> Unit,
) {

    val stateHandler = remember {
        UpdatePriorityDialogStateHandler(
            updatePriorityDialogViewModel = updatePriorityDialogViewModel,
            onNavigateBack = onNavigateBack
        )
    }

    UpdatePriorityDialog(
        modifier = modifier,
        stateHandler = stateHandler
    )
}

@Composable
fun UpdatePriorityDialog(
    modifier: Modifier = Modifier,
    stateHandler: UpdatePriorityDialogStateHandler,
) {

    val currentPriority by stateHandler.currentPriority.collectAsStateWithLifecycle()

    val priorityPickerState = rememberPriorityPickerState(
        initVisible = true,
        initPriority = currentPriority,
        onPickPriority = {
            stateHandler.onPickPriority(it)
            stateHandler.onNavigateBack()
        },
        onDismissRequest = stateHandler.onNavigateBack,
    )

    PriorityPickerDialog(
        modifier = modifier,
        priorityPickerState = priorityPickerState
    )

}