package at.robthered.simpletodo.features.homeScreen.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.domain.state.expandedItemsState.ExpandedItemsStateAction
import at.robthered.simpletodo.features.core.presentation.components.AppDeleteDialog
import at.robthered.simpletodo.features.core.presentation.components.AppDropdownMenu
import at.robthered.simpletodo.features.core.presentation.components.AppDropdownMenuItem
import at.robthered.simpletodo.features.core.presentation.components.AppMenuIconHorizontal
import at.robthered.simpletodo.features.core.presentation.components.AppToggleIcon
import at.robthered.simpletodo.features.core.presentation.navigation.MainRoute
import at.robthered.simpletodo.features.dataSource.domain.local.model.SectionModel
import at.robthered.simpletodo.features.homeScreen.presentation.HomeScreenAction
import at.robthered.simpletodo.features.homeScreen.presentation.components.remember.rememberDeleteDialogState
import at.robthered.simpletodo.features.homeScreen.presentation.components.remember.rememberSectionHeaderMenuState
import at.robthered.simpletodo.features.homeScreen.presentation.state.HomeStateHandler

@Composable
fun LazyItemScope.SectionItemHeader(
    item: SectionModel,
    modifier: Modifier = Modifier,
    isChildrenVisible: (sectionId: Long?) -> Boolean,
    taskCount: Int,
    stateHandler: HomeStateHandler,
) {


    val deleteDialogState = rememberDeleteDialogState(
        handleDelete =  {
            stateHandler.handleHomeStateManagerAction(
                HomeScreenAction.OnDeleteSection(item.sectionId)
            )
        }
    )

    val sectionHeaderMenuState = rememberSectionHeaderMenuState(
        sectionId = item.sectionId,
        createdAt = item.createdAt,
        onOpenUpdateSectionModal = {
            item.sectionId?.let {

                stateHandler.navigateTo(
                    MainRoute.UpdateSectionDialog(
                        sectionId = it
                    )
                )
            }
        },
        onAddTaskClick = {
            item.sectionId?.let {
                stateHandler.navigateTo(
                    MainRoute.AddTaskDialog(
                        taskOfSectionId = it
                    )
                )
            }
        },
        onOpenDeleteDialog = { deleteDialogState.onShow() }
    )



    val density = LocalDensity.current



    if (deleteDialogState.isVisible) {
        AppDeleteDialog(
            deleteDialogState = deleteDialogState,
        ) {
            Text(
                text = stringResource(R.string.dialog_delete_section_text)
            )
        }
    }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .onGloballyPositioned {
                sectionHeaderMenuState.updateRowWidth(it.size.width)
            }
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = item.title
            )
            Text(
                text = "$taskCount",
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )
        }
        AppToggleIcon(
            isIconVisible = taskCount > 0,
            rotateLeft = isChildrenVisible(item.sectionId),
            onToggle = {
                stateHandler.onExpandedSectionsStateAction(
                    ExpandedItemsStateAction
                        .OnToggleItemExpanded(
                            itemId = item.sectionId
                        )
                )
            }
        )
        AppMenuIconHorizontal(
            onClick = { sectionHeaderMenuState.onShow() }
        )
        AppDropdownMenu(
            expanded = sectionHeaderMenuState.isVisible,
            onDismissRequest = {
                sectionHeaderMenuState.onHide()
            },
            offset = DpOffset(
                x = with(density) { sectionHeaderMenuState.itemWidth.toDp() } - 32.dp,
                y = 0.dp,
            )
        ) {
            sectionHeaderMenuState.menuItems.map { menuItem ->
                AppDropdownMenuItem(
                    menuItem = menuItem
                )
            }
        }
    }
}