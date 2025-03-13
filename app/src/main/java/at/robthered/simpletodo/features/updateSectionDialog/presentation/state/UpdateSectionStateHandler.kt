package at.robthered.simpletodo.features.updateSectionDialog.presentation.state

import at.robthered.simpletodo.features.updateSectionDialog.domain.state.UpdateSectionStateAction
import at.robthered.simpletodo.features.updateSectionDialog.presentation.UpdateSectionViewModel

class UpdateSectionStateHandler(
    private val updateSectionViewModel: UpdateSectionViewModel,
    val onNavigateBack: () -> Unit,
) {
    val updateSectionState = updateSectionViewModel.updateSectionState
    fun onUpdateSectionStateAction(action: UpdateSectionStateAction) {
        updateSectionViewModel.onUpdateSectionStateAction(action)
    }

    val isLoading = updateSectionViewModel.isLoading
}