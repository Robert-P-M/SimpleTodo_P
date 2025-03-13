package at.robthered.simpletodo.features.core.presentation.components.picker.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class PickerState {
    var selectedItem by mutableStateOf("")
}