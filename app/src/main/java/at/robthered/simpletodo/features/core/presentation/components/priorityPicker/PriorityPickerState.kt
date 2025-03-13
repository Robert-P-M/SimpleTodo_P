package at.robthered.simpletodo.features.core.presentation.components.priorityPicker

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.presentation.UiText
import at.robthered.simpletodo.features.core.presentation.icons.PriorityHigh
import at.robthered.simpletodo.features.core.presentation.icons.PriorityLow
import at.robthered.simpletodo.features.core.presentation.icons.PriorityNormal
import at.robthered.simpletodo.features.core.presentation.uiModel.PriorityItem
import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityEnum


class PriorityPickerState(
    val initPriority: PriorityEnum?,
    val initVisible: Boolean,
    private val onPickPriority: (priority: PriorityEnum?) -> Unit,
    private val onDismissRequest: () -> Unit
) {

    var isVisible by mutableStateOf(initVisible)
        private set

    fun onShow(){
        isVisible = true
    }
    fun onHide() {
        isVisible = false
        onDismissRequest()
    }

    fun setPriority(priority: PriorityEnum?) {
        onPickPriority(priority)
        onHide()
    }

    val priorityItems = buildList {
        add(
            PriorityItem(
                text = UiText.StringResourceId(R.string.priority_enum_text_low),
                leadingIcon = PriorityLow,
                priority = PriorityEnum.LOW
            )
        )
        add(
            PriorityItem(
                text = UiText.StringResourceId(R.string.priority_enum_text_normal),
                leadingIcon = PriorityNormal,
                priority = PriorityEnum.NORMAL
            )
        )
        add(
            PriorityItem(
                text = UiText.StringResourceId(R.string.priority_enum_text_high),
                leadingIcon = PriorityHigh,
                priority = PriorityEnum.HIGH
            )
        )
    }

}