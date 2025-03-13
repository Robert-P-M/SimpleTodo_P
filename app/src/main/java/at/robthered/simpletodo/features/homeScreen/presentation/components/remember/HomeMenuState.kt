package at.robthered.simpletodo.features.homeScreen.presentation.components.remember

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.AutoAwesomeMosaic
import androidx.compose.material.icons.outlined.Language
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.presentation.UiText
import at.robthered.simpletodo.features.core.presentation.navigation.MainRoute
import at.robthered.simpletodo.features.core.presentation.uiModel.MenuItem

class HomeMenuState(
    private val onNavigateToAddSectionDialog: (args: MainRoute.AddSectionDialog) -> Unit,
    private val onOpenLocalePicker: () -> Unit,
) {

    var isVisible by mutableStateOf(false)
        private set

    fun onShow(){
        isVisible = true
    }
    fun onHide() {
        isVisible = false
    }
    val menuItems = listOf(
        MenuItem.Default(
            text = UiText.StringResourceId(R.string.home_top_app_bar_dropdown_menu_item_text_view_mode),
            leadingIcon = Icons.Outlined.AutoAwesomeMosaic,
            iconDescription = UiText.StringResourceId(R.string.home_top_app_bar_dropdown_menu_item_icon_description_view_mode)
        ),
        MenuItem.Default(
            text = UiText.StringResourceId(R.string.home_top_app_bar_dropdown_menu_item_text_add_section),
            leadingIcon = Icons.Outlined.AddCircle,
            iconDescription = UiText.StringResourceId(R.string.home_top_app_bar_dropdown_menu_item_icon_description_add_section),
            onClick = {
                onNavigateToAddSectionDialog(MainRoute.AddSectionDialog())
                onHide()
            }
        ),
        MenuItem.Default(
            text = UiText.StringResourceId(R.string.home_top_app_bar_dropdown_menu_item_text_change_language),
            leadingIcon = Icons.Outlined.Language,
            iconDescription = UiText.StringResourceId(R.string.home_top_app_bar_dropdown_menu_item_icon_description_change_language),
            onClick = {
                onOpenLocalePicker()
                onHide()
            }
        ),
        MenuItem.Close(
            onClick = {
                onHide()
            },
        )

    )
}