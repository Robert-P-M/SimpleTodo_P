package at.robthered.simpletodo.features.core.presentation.uiModel

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import at.robthered.simpletodo.features.core.presentation.UiText

sealed class MenuItem {
    data class Header(
        val title: UiText,
    ) : MenuItem()

    data class Default(
        val text: UiText,
        val leadingIcon: ImageVector? = null,
        val iconTint: Color? = null,
        val iconDescription: UiText? = null,
        val onClick: () -> Unit = {},
    ) : MenuItem()

    data class Action(
        val text: UiText,
        val leadingIcon: ImageVector? = null,
        val iconDescription: UiText? = null,
        val onClick: () -> Unit = {},
        val textColor: Color? = null,
        val iconTint: Color? = null,
        val backgroundColor: Color? = null,
    ) : MenuItem()

    data class Close(
        val onClick: () -> Unit,
    ) : MenuItem()
}