package at.robthered.simpletodo.features.homeScreen.presentation.components.remember

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
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
import at.robthered.simpletodo.features.core.utils.ext.toFormattedForMenuHeader
import kotlinx.datetime.Instant


class SectionHeaderMenuState(
    private val createdAt: Instant,
    private val onOpenUpdateSectionModal: () -> Unit,
    private val onOpenDeleteDialog: () -> Unit,
    private val onAddTaskClick: () -> Unit,
    deleteTextColor: Color,
    deleteIconTint: Color,
    deleteBackgroundColor: Color,
) {
    var isVisible by mutableStateOf(false)

    var itemWidth by mutableIntStateOf(0)

    fun toggleMenu() {
        isVisible = isVisible.not()
    }

    fun onShow() {
        isVisible = true
    }

    fun onHide() {
        isVisible = false
    }


    fun updateRowWidth(width: Int) {
        itemWidth = width
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
                text = UiText.StringResourceId(R.string.task_item_dropdown_menu_item_text_edit_section),
                leadingIcon = Icons.Outlined.Edit,
                iconDescription = UiText.StringResourceId(R.string.task_item_dropdown_menu_item_icon_description_edit_section),
                onClick = {
                    onOpenUpdateSectionModal()
                    onHide()
                }
                )
            )
            add(
                MenuItem.Default(
                    text = UiText.StringResourceId(R.string.section_item_dropdown_menu_item_text_add_task),
                    leadingIcon = Icons.Outlined.AddCircle,
                    iconDescription = UiText.StringResourceId(R.string.section_item_dropdown_menu_item_icon_description_add_task),
                    onClick = {
                        onAddTaskClick()
                        onHide()
                    }
                )
            )

            add(
                MenuItem.Action(
                    text = UiText.StringResourceId(R.string.task_item_dropdown_menu_item_text_delete_section),
                    leadingIcon = Icons.Outlined.Delete,
                    iconDescription = UiText.StringResourceId(R.string.task_item_dropdown_menu_item_icon_description_delete_section),
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