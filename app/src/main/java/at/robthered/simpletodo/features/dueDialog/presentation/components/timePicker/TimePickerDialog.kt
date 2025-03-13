package at.robthered.simpletodo.features.dueDialog.presentation.components.timePicker

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.presentation.components.AppDialog
import at.robthered.simpletodo.features.core.presentation.components.AppFilledButton
import at.robthered.simpletodo.features.core.presentation.components.picker.Picker
import at.robthered.simpletodo.features.core.utils.ext.getHour
import at.robthered.simpletodo.features.core.utils.ext.getMinute


@Composable
fun TimePickerDialog(
    timePickerState: TimePickerState,
) {

    AnimatedVisibility(
        visible = timePickerState.isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        AppDialog(
            modifier = Modifier.padding(
                horizontal = 16.dp, vertical =
                8.dp
            ),
            onDismissRequest = timePickerState::onHide
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(R.string.time_picker_dialog_title))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Picker(
                        items = (0..23).map { it.toString() }.toList(),
                        startIndex = timePickerState.currentInstant.getHour(),
                        visibleItemsCount = 5,
                        modifier = Modifier.weight(0.45f),
                        textModifier = Modifier.padding(8.dp),
                        onChangeValue = { item ->
                            item.toIntOrNull()?.let {
                                timePickerState.setHour(it)
                            }
                        },
                        textStyle = MaterialTheme.typography.bodyMedium,
                        dividerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                        label = {
                            Text(
                                modifier = it,
                                text = stringResource(R.string.wheel_picker_label_hour)
                            )
                        }
                    )
                    Text(text = " : ")

                    Picker(
                        items = (0..59).map { it.toString() }.toList(),
                        startIndex = timePickerState.currentInstant.getMinute(),
                        visibleItemsCount = 5,
                        modifier = Modifier.weight(0.45f),
                        textModifier = Modifier.padding(8.dp),
                        onChangeValue = { item ->
                            item.toIntOrNull()?.let {
                                timePickerState.setMinute(it)
                            }
                        },
                        textStyle = MaterialTheme.typography.bodyMedium,
                        dividerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                        label = {
                            Text(
                                modifier = it,
                                text = stringResource(R.string.wheel_picker_label_minute)
                            )
                        }
                    )
                }
                AppFilledButton(
                    enabled = true,
                    text = "Save",
                    onClick = {
                        timePickerState.save()
                    }
                )
                AppFilledButton(
                    enabled = true,
                    text = "Reset",
                    onClick = {
                        timePickerState.clear()
                    }
                )
            }
        }
    }
}