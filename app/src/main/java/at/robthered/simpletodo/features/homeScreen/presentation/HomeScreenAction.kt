package at.robthered.simpletodo.features.homeScreen.presentation

sealed interface HomeScreenAction {
    data class OnDeleteTask(val taskId: Long?): HomeScreenAction
    data class OnDeleteSection(val sectionId: Long?): HomeScreenAction
}