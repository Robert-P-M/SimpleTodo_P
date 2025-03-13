package at.robthered.simpletodo.features.core.presentation.components.taskLinks

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.presentation.components.AppCloseButton
import at.robthered.simpletodo.features.core.presentation.components.AppFilledButton
import at.robthered.simpletodo.features.core.presentation.components.AppLoadingSpinner
import at.robthered.simpletodo.features.core.presentation.components.AppModalBottomSheet
import at.robthered.simpletodo.features.core.presentation.components.AppModalTitle
import at.robthered.simpletodo.features.core.presentation.components.taskLinks.remember.TasksLinkDialogState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskLinksDialog(
    modifier: Modifier = Modifier,
    tasksLinkDialogState: TasksLinkDialogState,
    linksDialogItems: LazyListScope.() -> Unit,
) {

    if(tasksLinkDialogState.isVisible) {
        AppModalBottomSheet(
            modifier = modifier,
            onDismissRequest = {
                tasksLinkDialogState.onHide()
            }
        ) {
            val linksListState = rememberLazyListState()
            val flingBehaviour = rememberSnapFlingBehavior(lazyListState = linksListState, snapPosition = SnapPosition.Center)
            LazyColumn(
                modifier = Modifier,
                state = linksListState,
                flingBehavior = flingBehaviour,
            ) {
                stickyHeader {
                    AppModalTitle(
                        modifier = Modifier
                            .background(
                                BottomSheetDefaults.ContainerColor
                            )
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        title = stringResource(id = R.string.links_dialog_title),
                        leadingIcon = {
                                Icon(
                                    modifier = Modifier.align(Alignment.CenterStart).clickable {
                                        tasksLinkDialogState.onAddItemClick() },
                                    imageVector = Icons.Outlined.Add,
                                    contentDescription = null
                                )
                        },
                        trailingIcon = {
                            AppCloseButton(
                                onClick = {
                                    tasksLinkDialogState.onHide()
                                }
                            )
                        }
                    )
                }
                item {
                    AppFilledButton(
                        enabled = true,
                        onClick = {
                            tasksLinkDialogState.onAddItemClick()
                        },
                        text = stringResource(R.string.add_new_link)
                    )
                }
                linksDialogItems()
                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
                item {
                    HorizontalDivider(
                        thickness = 6.dp,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
            }
        }
    }
}