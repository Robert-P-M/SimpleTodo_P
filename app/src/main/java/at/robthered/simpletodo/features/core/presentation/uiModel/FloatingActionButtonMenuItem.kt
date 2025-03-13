package at.robthered.simpletodo.features.core.presentation.uiModel

import androidx.compose.ui.graphics.vector.ImageVector
import at.robthered.simpletodo.features.core.presentation.UiText

sealed class FloatingActionButtonMenuItem {
    data class Default(
        val text: UiText,
        val onClick: () -> Unit,
        val icon: ImageVector,
    )
}