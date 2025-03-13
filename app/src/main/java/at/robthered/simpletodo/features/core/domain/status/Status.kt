package at.robthered.simpletodo.features.core.domain.status

import at.robthered.simpletodo.features.core.presentation.UiText

interface Status {
    fun toUiText(): UiText
}