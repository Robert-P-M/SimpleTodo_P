package at.robthered.simpletodo.features.updatePriorityDialog.presentation.state

import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityEnum
import at.robthered.simpletodo.features.updatePriorityDialog.presentation.UpdatePriorityDialogViewModel

class UpdatePriorityDialogStateHandler(
    private val updatePriorityDialogViewModel: UpdatePriorityDialogViewModel,
    val onNavigateBack: () -> Unit,
) {
    val currentPriority = updatePriorityDialogViewModel.currentPriority
    fun onPickPriority(priorityEnum: PriorityEnum?) {
        updatePriorityDialogViewModel.onPickPriority(priorityEnum)
    }
}