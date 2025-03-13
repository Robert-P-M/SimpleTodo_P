package at.robthered.simpletodo.features.core.presentation.components.priorityPicker

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.robthered.simpletodo.features.core.presentation.components.AppDialog
import at.robthered.simpletodo.features.core.presentation.uiModel.PriorityItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PriorityPickerDialog(
    modifier: Modifier = Modifier,
    priorityPickerState: PriorityPickerState,
) {
    if (priorityPickerState.isVisible) {

        val priorityItems = remember {
            priorityPickerState.priorityItems
        }
        AppDialog(
            modifier = modifier,
            onDismissRequest = {
                priorityPickerState.onHide()
            }
        ) {
            val priorityListState = rememberLazyListState()

            LazyColumn(
                state = priorityListState,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 400.dp)
            ) {
                stickyHeader {
                    PriorityPickerDialogItem(
                        modifier = modifier,
                        onDismissRequest = priorityPickerState::onHide,
                    )
                }
                item {

                    PriorityPickerDialogItem(
                        modifier = Modifier,
                        onPickPriority = {
                            priorityPickerState.setPriority(it)
                        }
                    )
                }
                items(priorityItems) { item: PriorityItem ->
                    PriorityPickerDialogItem(
                        modifier = Modifier,
                        onPickPriority = priorityPickerState::setPriority,
                        item = item,
                        currentPriority = priorityPickerState.initPriority,
                    )
                }
            }
        }
    }
}