package at.robthered.simpletodo.features.dataSource.presentation.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Unarchive
import androidx.compose.ui.graphics.vector.ImageVector
import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.presentation.UiText
import at.robthered.simpletodo.features.dataSource.domain.local.model.ArchivedModel

fun ArchivedModel.activityText(): UiText {
    return when (this.isArchived) {
        true -> UiText.StringResourceId(id = R.string.task_activity_dialog_archived_activity_text_is_archived)
        false -> UiText.StringResourceId(id = R.string.task_activity_dialog_archived_activity_text_is_not_archived)
    }
}

fun ArchivedModel.activityIcon(): ImageVector {
    return when(this.isArchived) {
        true -> Icons.Outlined.Archive
        false -> Icons.Outlined.Unarchive
    }
}