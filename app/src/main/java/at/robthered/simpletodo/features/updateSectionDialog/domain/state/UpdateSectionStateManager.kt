package at.robthered.simpletodo.features.updateSectionDialog.domain.state

import kotlinx.coroutines.flow.StateFlow

interface UpdateSectionStateManager {
    val state: StateFlow<UpdateSectionState>
    fun handleAction(action: UpdateSectionStateAction)
}