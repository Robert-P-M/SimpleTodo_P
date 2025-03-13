package at.robthered.simpletodo.features.priorityDialogScreen.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import at.robthered.simpletodo.features.core.domain.state_manager.priority.PriorityStateManagerAction
import at.robthered.simpletodo.features.core.presentation.components.priorityPicker.PriorityPickerDialog
import at.robthered.simpletodo.features.core.presentation.components.priorityPicker.rememberPriorityPickerState
import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun PriorityPickerDialogScreenRoot(
    modifier: Modifier = Modifier,
    priorityPickerDialogViewModel: PriorityPickerDialogViewModel = koinViewModel<PriorityPickerDialogViewModel>(),
    onNavigateBack: () -> Unit,
) {
    val priorityModel: PriorityModel by priorityPickerDialogViewModel.priorityModel.collectAsStateWithLifecycle()

    PriorityPickerDialogScreen(
        modifier = modifier,
        priorityModel = priorityModel,
        handleAction = { action: PriorityPickerDialogAction ->
            when (action) {
                PriorityPickerDialogAction.OnNavigateBack -> onNavigateBack()
                else -> Unit
            }
            priorityPickerDialogViewModel.handleAction(action)
        }
    )

}

@Composable
fun PriorityPickerDialogScreen(
    modifier: Modifier = Modifier,
    priorityModel: PriorityModel,
    handleAction: (action: PriorityPickerDialogAction) -> Unit,
) {

    val priorityPickerState = rememberPriorityPickerState(
        initVisible = true,
        initPriority = priorityModel.priority,
        onPickPriority = {
            handleAction(
                PriorityPickerDialogAction
                    .PriorityStateAction(
                        PriorityStateManagerAction
                            .OnChangePriority(it)
                    )
            )
            handleAction(
                PriorityPickerDialogAction
                    .OnSendPriorityModel
            )
        },
        onDismissRequest = {
            handleAction(
                PriorityPickerDialogAction
                    .OnNavigateBack
            )
        },
    )

    PriorityPickerDialog(
        modifier = modifier,
        priorityPickerState = priorityPickerState
    )
}