package at.robthered.simpletodo.features.core.domain.status

import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.presentation.UiText

@androidx.annotation.Keep
enum class UpdateTaskStatus: Status {
    SAVING {
        override fun toUiText(): UiText {
            return UiText.StringResourceId(id = R.string.loading_info_start_updating_task)
        }
    },
    Done {
        override fun toUiText(): UiText {
            return UiText.StringResourceId(id = R.string.loading_info_updating_task_done)
        }
    }
}