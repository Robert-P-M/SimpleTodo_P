package at.robthered.simpletodo.features.core.presentation.uiModel

import androidx.compose.ui.graphics.vector.ImageVector
import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityEnum
import at.robthered.simpletodo.features.core.presentation.UiText

data class PriorityItem(
    val priority: PriorityEnum,
    val text: UiText,
    val leadingIcon: ImageVector,
)