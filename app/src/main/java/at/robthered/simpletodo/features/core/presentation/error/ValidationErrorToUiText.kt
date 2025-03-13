package at.robthered.simpletodo.features.core.presentation.error

import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.domain.error.ValidationError
import at.robthered.simpletodo.features.core.presentation.UiText

fun ValidationError.toUiText(): UiText {
    return when (this) {
        ValidationError.TaskTitle.EMPTY -> UiText.StringResourceId(
            id = R.string.error_add_task_title_empty,
            args = arrayOf(ValidationError.TaskTitle.EMPTY.minLength)
        )

        ValidationError.TaskTitle.TOO_SHORT -> UiText.StringResourceId(
            id = R.string.error_add_task_title_too_short,
            args = arrayOf(ValidationError.TaskTitle.TOO_SHORT.minLength)
        )

        ValidationError.TaskDescription.EMPTY -> UiText.StringResourceId(
            id = R.string.error_add_task_description_too_short,
            args = arrayOf(ValidationError.TaskDescription.EMPTY.minLength)
        )

        ValidationError.TaskDescription.TOO_SHORT -> UiText.StringResourceId(
            id = R.string.error_add_task_description_empty,
            args = arrayOf(ValidationError.TaskDescription.TOO_SHORT.minLength)
        )

        ValidationError.SectionTitle.EMPTY -> UiText.StringResourceId(
            id = R.string.error_add_section_title_empty,
            args = arrayOf(ValidationError.SectionTitle.EMPTY.minLength)
        )

        ValidationError.SectionTitle.TOO_SHORT -> UiText.StringResourceId(
            id = R.string.error_add_section_too_short,
            args = arrayOf(ValidationError.SectionTitle.TOO_SHORT.minLength)
        )

        ValidationError.LinkUrl.EMPTY -> UiText.StringResourceId(
            id = R.string.error_shared_url_empty,
        )

        ValidationError.LinkUrl.INVALID -> UiText.StringResourceId(
            id = R.string.error_shared_url_invalid
        )

        ValidationError.LinkUrl.NO_HTTP_PROTOCOL -> UiText.StringResourceId(
            id = R.string.error_shared_url_no_http_protocol
        )

        ValidationError.LinkUrl.MALFORMED -> UiText.StringResourceId(
            id = R.string.error_shared_url_malformed,
        )

        ValidationError.LinkUrl.TOO_LONG -> UiText.StringResourceId(
            id = R.string.error_shared_url_too_long,
            args = arrayOf(ValidationError.LinkUrl.TOO_LONG.maxLength)
        )

         ValidationError.LinkDescription.EMPTY -> UiText.StringResourceId(
             id = R.string.error_link_description_empty,
             args = arrayOf(ValidationError.LinkUrl.TOO_LONG.maxLength)
         )
         ValidationError.LinkDescription.TOO_SHORT -> UiText.StringResourceId(
             id = R.string.error_link_description_too_short,
             args = arrayOf(ValidationError.LinkUrl.TOO_LONG.maxLength)
         )
         ValidationError.LinkTitle.EMPTY -> UiText.StringResourceId(
             id = R.string.error_link_title_empty,
             args = arrayOf(ValidationError.LinkUrl.TOO_LONG.maxLength)
         )
         ValidationError.LinkTitle.TOO_SHORT -> UiText.StringResourceId(
             id = R.string.error_link_title_too_long,
             args = arrayOf(ValidationError.LinkUrl.TOO_LONG.maxLength)
         )
    }
}