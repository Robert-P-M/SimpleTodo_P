package at.robthered.simpletodo.features.dataSource.presentation.model

import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.presentation.UiText
import at.robthered.simpletodo.features.dataSource.domain.local.model.LinkModel

fun LinkModel.activityText(): UiText {
    return UiText.StringResourceId(id = R.string.task_activity_dialog_add_link, arrayOf(this.title))
}