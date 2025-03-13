package at.robthered.simpletodo.features.taskDetailsDialog.presentation.components.remember

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ShowChart
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.presentation.UiText
import at.robthered.simpletodo.features.core.presentation.uiModel.MenuItem

class TaskDetailsMenuState(
    private val onNavigateToTaskActivityDialog: () -> Unit,
    private val onOpenUpdateTaskModal: () -> Unit,
    onOpenDeleteDialog: () -> Unit,
    deleteTextColor: Color,
    deleteIconTint: Color,
    deleteBackgroundColor: Color,
) {
    var isVisible by mutableStateOf(false)
        private set

    var xOffset by mutableIntStateOf(0)

    var yOffset by mutableIntStateOf(0)

    fun updateRowEnd(width: Int) {
        xOffset = width
    }
    fun updateRowTop(height: Int) {
        yOffset = height
    }

    fun onShow(){
        isVisible = true
    }
    fun onHide() {
        isVisible = false
    }
    val menuItems = listOf(
        MenuItem.Default(
            text = UiText.StringResourceId(R.string.task_details_menu_item_open_task_activity_dialog),
            leadingIcon = Icons.AutoMirrored.Outlined.ShowChart,
            iconDescription = UiText.StringResourceId(R.string.home_top_app_bar_dropdown_menu_item_icon_description_add_section),
            onClick = {
                onNavigateToTaskActivityDialog()
                onHide()
            }
        ),
        MenuItem.Default(text = UiText.StringResourceId(R.string.task_item_dropdown_menu_item_text_edit_task),
            leadingIcon = Icons.Outlined.Edit,
            iconDescription = UiText.StringResourceId(R.string.task_item_dropdown_menu_item_icon_description_edit_task),
            onClick = {
                onOpenUpdateTaskModal()
                onHide()
            }
        ),
        MenuItem.Action(
            text = UiText.StringResourceId(R.string.task_item_dropdown_menu_item_text_delete_task),
            leadingIcon = Icons.Outlined.Delete,
            iconDescription = UiText.StringResourceId(R.string.task_item_dropdown_menu_item_icon_description_delete_task),
            onClick = {
                onOpenDeleteDialog()
                onHide()
            },
            textColor = deleteTextColor,
            iconTint = deleteIconTint,
            backgroundColor = deleteBackgroundColor
        ),
        MenuItem.Close(
            onClick = {
                onHide()
            },
        )
    )
}