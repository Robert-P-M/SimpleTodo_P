package at.robthered.simpletodo.features.core.domain.status

import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.presentation.UiText

@androidx.annotation.Keep
enum class SaveImageStatus: Status {
    SAVING {
        override fun toUiText(): UiText {
            return UiText.StringResourceId(id = R.string.loading_info_processing_image_start)
        }
    },
    DONE {
        override fun toUiText(): UiText {
            return UiText.StringResourceId(id = R.string.loading_info_processing_image_completed)
        }
    },
}