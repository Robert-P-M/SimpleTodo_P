package at.robthered.simpletodo.features.dataSource.presentation.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.ui.graphics.vector.ImageVector
import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.presentation.UiText
import at.robthered.simpletodo.features.dataSource.domain.local.model.CompletedModel


fun CompletedModel.activityIcon(): ImageVector {
    return when(this.isCompleted) {
        true -> Icons.Outlined.CheckCircle
        false -> Icons.Outlined.Circle
    }
}

fun CompletedModel.activityText(): UiText {
    return when(this.isCompleted) {
        true -> UiText.StringResourceId(id = R.string.task_activity_dialog_completed_activity_text_is_completed)
        false -> UiText.StringResourceId(id = R.string.task_activity_dialog_completed_activity_text_is_not_completed)
    }
}