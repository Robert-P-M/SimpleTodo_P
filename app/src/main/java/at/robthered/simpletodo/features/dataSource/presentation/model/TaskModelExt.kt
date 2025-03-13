package at.robthered.simpletodo.features.dataSource.presentation.model

import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.presentation.UiText
import at.robthered.simpletodo.features.dataSource.domain.local.model.TaskModel

fun TaskModel.activityText(): UiText {
    return UiText.StringResourceId(id = R.string.task_activity_dialog_task_creation, arrayOf(this.title))
}

fun TaskModel.activityText(isFirst: Boolean): UiText {
    return UiText.PluralResourceId(id = R.plurals.task_activity_dialog_child_task_text, count = if(isFirst) 1 else 9999, args = arrayOf(this.title))
}