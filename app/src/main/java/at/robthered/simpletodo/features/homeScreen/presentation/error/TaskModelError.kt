package at.robthered.simpletodo.features.homeScreen.presentation.error

import at.robthered.simpletodo.features.core.presentation.UiText

data class TaskModelError(
    val titleError: UiText? = null,
    val descriptionError: UiText? = null,
)