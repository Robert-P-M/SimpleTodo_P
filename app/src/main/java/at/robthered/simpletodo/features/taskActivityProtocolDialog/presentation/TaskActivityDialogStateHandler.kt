package at.robthered.simpletodo.features.taskActivityProtocolDialog.presentation

class TaskActivityDialogStateHandler(
    private val taskActivityDialogViewModel: TaskActivityDialogViewModel,
    val onNavigateBack: () -> Unit,
) {

    val activities = taskActivityDialogViewModel.activities
}