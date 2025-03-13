package at.robthered.simpletodo.features.dueDialog.presentation.components.calendarDay

data class CalendarDayUiModel(
    val isCurrentDay: Boolean,
    val isPickedDay: Boolean,
    val isEnabled: Boolean,
    val itemAlpha: Float,
    val text: String,
    val onClick: () -> Unit
)