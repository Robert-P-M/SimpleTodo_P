package at.robthered.simpletodo.features.dueDialog.presentation.state

import at.robthered.simpletodo.features.core.domain.state_manager.dueEvent.DueEventStateManagerAction
import at.robthered.simpletodo.features.dueDialog.presentation.DueDialogViewModel

class DueDialogScreenStateHandler(
    private val dueDialogViewModel: DueDialogViewModel,
    private val onNavigateBack: () -> Unit,
) {

    val dueEventState = dueDialogViewModel.dueEventState
    fun handleDueEventStateAction(action: DueEventStateManagerAction) = dueDialogViewModel.handleDueEventStateAction(action)

    fun sendEventModel() = dueDialogViewModel.sendEventModel()

    fun back() {
        onNavigateBack()
        handleDueEventStateAction(
            DueEventStateManagerAction.OnClearState
        )
    }

    val currentAppDateTime = dueDialogViewModel.currentAppDateTime
    val currentAppDate = dueDialogViewModel.currentAppDate
    val upcomingDays = dueDialogViewModel.upcomingDays
    val headerDaysOfWeek = dueDialogViewModel.headerDaysOfWeek
    val pagedMonths = dueDialogViewModel.pagedMonths

}