package at.robthered.simpletodo.features.homeScreen.presentation.components.remember

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ShowChart
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.presentation.UiText
import at.robthered.simpletodo.features.core.presentation.uiModel.MenuItem
import at.robthered.simpletodo.features.core.utils.ext.toFormattedForMenuHeader
import kotlinx.datetime.Instant

class TaskCardMenuState(
    private val createdAt: Instant,
    private val onOpenUpdateTaskModal: () -> Unit,
    private val onAddSubtaskClick: () -> Unit,
    private val onNavigateToDetails: () -> Unit,
    private val isMaxDepthReached: Boolean,
    private val onOpenPriorityPicker: () -> Unit,
    private val onOpenDeleteDialog: () -> Unit,
    deleteTextColor: Color,
    deleteIconTint: Color,
    deleteBackgroundColor: Color,
    priorityText: UiText,
    priorityIcon: ImageVector,
    priorityColor: Color,
    private val onNavigateToTaskActivityLogDialog: () -> Unit,
) {
    var isVisible by mutableStateOf(false)
        private set

    fun onShow() {
        isVisible = true
    }

    fun onHide() {
        isVisible = false
    }

    val menuItems: List<MenuItem> =
        buildList {
            add(
                MenuItem.Header(
                    title = UiText.StringResourceId(
                        R.string.task_item_dropdown_menu_item_text_created_at,
                        arrayOf(createdAt.toFormattedForMenuHeader())
                    ),
                )
            )
            add(
                MenuItem.Default(
                    text = UiText.StringResourceId(R.string.task_details_menu_item_open_task_activity_dialog),
                    leadingIcon = Icons.AutoMirrored.Outlined.ShowChart,
                    iconDescription = UiText.StringResourceId(R.string.home_top_app_bar_dropdown_menu_item_icon_description_add_section),
                    onClick = {
                        onNavigateToTaskActivityLogDialog()
                        onHide()
                    }
                )
            )
            add(
                MenuItem.Default(text = UiText.StringResourceId(R.string.task_item_dropdown_menu_item_text_edit_task),
                    leadingIcon = Icons.Outlined.Edit,
                    iconDescription = UiText.StringResourceId(R.string.task_item_dropdown_menu_item_icon_description_edit_task),
                    onClick = {
                        onOpenUpdateTaskModal()
                        onHide()
                    })
            )
            if (!isMaxDepthReached) {
                add(
                    MenuItem.Default(
                        text = UiText.StringResourceId(R.string.task_item_dropdown_menu_item_text_add_sub_task),
                        leadingIcon = Icons.Outlined.AddCircle,
                        iconDescription = UiText.StringResourceId(R.string.task_item_dropdown_menu_item_icon_description_add_sub_task),
                        onClick = {
                            onAddSubtaskClick()
                            onHide()
                        },
                    )
                )

            }
            add(
                MenuItem.Default(
                    text = UiText.StringResourceId(R.string.task_item_dropdown_menu_item_text_go_to_details),
                    leadingIcon = Icons.Outlined.ChevronRight,
                    iconDescription = UiText.StringResourceId(R.string.task_item_dropdown_menu_item_icon_description_go_to_details),
                    onClick = {
                        onHide()
                        onNavigateToDetails()
                    },
                )
            )
            add(
                MenuItem.Action(
                    text = priorityText,
                    leadingIcon = priorityIcon,
                    iconTint = priorityColor,
                    onClick = {
                        onOpenPriorityPicker()
                        onHide()
                    },
                )
            )
            add(
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
                )
            )
            add(
                MenuItem.Close(
                    onClick = {
                        onHide()
                    },
                )
            )
        }

}

class DeleteDialogState(
    private val handleDelete: () -> Unit,
) {
    var isVisible by mutableStateOf(false)
        private set

    fun onDeleteClick() {
        onHide()
        handleDelete()
    }

    fun onShow() {
        isVisible = true
    }

    fun onHide() {
        isVisible = false
    }
}