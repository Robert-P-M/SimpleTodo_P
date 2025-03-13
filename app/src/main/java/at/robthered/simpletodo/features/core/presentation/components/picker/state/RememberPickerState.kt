package at.robthered.simpletodo.features.core.presentation.components.picker.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun rememberPickerState() = remember { PickerState() }