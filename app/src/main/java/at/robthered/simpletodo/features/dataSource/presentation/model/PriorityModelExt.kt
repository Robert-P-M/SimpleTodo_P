package at.robthered.simpletodo.features.dataSource.presentation.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotInterested
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.presentation.UiText
import at.robthered.simpletodo.features.core.presentation.icons.PriorityHigh
import at.robthered.simpletodo.features.core.presentation.icons.PriorityLow
import at.robthered.simpletodo.features.core.presentation.icons.PriorityNormal
import at.robthered.simpletodo.features.core.presentation.icons.PriorityPicker
import at.robthered.simpletodo.features.core.presentation.theme.priorityHigh
import at.robthered.simpletodo.features.core.presentation.theme.priorityLow
import at.robthered.simpletodo.features.core.presentation.theme.priorityNormal
import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityEnum
import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityModel

fun PriorityModel?.toUiText(): UiText {
    return when (this?.priority) {
        PriorityEnum.HIGH -> UiText.StringResourceId(R.string.priority_enum_text_high)
        PriorityEnum.NORMAL -> UiText.StringResourceId(R.string.priority_enum_text_normal)
        PriorityEnum.LOW -> UiText.StringResourceId(R.string.priority_enum_text_low)
        else -> UiText.StringResourceId(R.string.priority_enum_text_no_selection)
    }
}

fun PriorityModel?.icon(): ImageVector {
    return when (this?.priority) {
        PriorityEnum.HIGH -> PriorityHigh
        PriorityEnum.NORMAL -> PriorityNormal
        PriorityEnum.LOW -> PriorityLow
        else -> PriorityPicker
    }
}

@Composable
fun PriorityModel?.color(): Color {
    return when(this?.priority){
        PriorityEnum.HIGH -> MaterialTheme.colorScheme.priorityHigh
        PriorityEnum.NORMAL -> MaterialTheme.colorScheme.priorityNormal
        PriorityEnum.LOW -> MaterialTheme.colorScheme.priorityLow
        null -> MaterialTheme.colorScheme.onSurfaceVariant
    }
}

fun PriorityModel.activityIcon(): ImageVector {
    return when (this.priority) {
        PriorityEnum.HIGH -> PriorityHigh

        PriorityEnum.NORMAL -> PriorityNormal

        PriorityEnum.LOW -> PriorityLow
        else -> Icons.Default.NotInterested
    }
}

fun PriorityModel.activityText(): UiText {
    return when (this.priority) {
        PriorityEnum.HIGH -> UiText.StringResourceId(id = R.string.task_activity_dialog_priority_enum_activity_text_high)
        PriorityEnum.NORMAL -> UiText.StringResourceId(id = R.string.task_activity_dialog_priority_enum_activity_text_normal)
        PriorityEnum.LOW -> UiText.StringResourceId(id = R.string.task_activity_dialog_priority_enum_activity_text_low)
        null -> UiText.StringResourceId(id = R.string.task_activity_dialog_priority_enum_activity_text_none)
    }
}

@Composable
fun PriorityModel?.activityTint(default: Color = MaterialTheme.colorScheme.onSurfaceVariant): Color {

    return when (this?.priority) {
        PriorityEnum.HIGH -> MaterialTheme.colorScheme.priorityHigh
        PriorityEnum.NORMAL -> MaterialTheme.colorScheme.priorityNormal
        PriorityEnum.LOW -> MaterialTheme.colorScheme.priorityLow
        else -> default
    }
}