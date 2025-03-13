package at.robthered.simpletodo.features.dataSource.presentation.model

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

fun PriorityEnum?.icon(): ImageVector {
    return when (this) {
        PriorityEnum.HIGH -> PriorityHigh

        PriorityEnum.NORMAL -> PriorityNormal

        PriorityEnum.LOW -> PriorityLow
        else -> PriorityPicker
    }
}

@Composable
fun PriorityEnum.iconTint(): Color {

    return when (this) {
        PriorityEnum.HIGH -> MaterialTheme.colorScheme.priorityHigh
        PriorityEnum.NORMAL -> MaterialTheme.colorScheme.priorityNormal
        PriorityEnum.LOW -> MaterialTheme.colorScheme.priorityLow
    }
}

@Composable
fun PriorityEnum?.iconTint(default: Color = MaterialTheme.colorScheme.onSurfaceVariant): Color {

    return when (this) {
        PriorityEnum.HIGH -> MaterialTheme.colorScheme.priorityHigh
        PriorityEnum.NORMAL -> MaterialTheme.colorScheme.priorityNormal
        PriorityEnum.LOW -> MaterialTheme.colorScheme.priorityLow
        else -> default
    }
}


fun PriorityEnum?.uiText(): UiText {
    return when (this) {
        PriorityEnum.HIGH -> UiText.StringResourceId(R.string.priority_enum_text_high)
        PriorityEnum.NORMAL -> UiText.StringResourceId(R.string.priority_enum_text_normal)
        PriorityEnum.LOW -> UiText.StringResourceId(R.string.priority_enum_text_low)
        else -> UiText.StringResourceId(R.string.priority_enum_text_no_selection)
    }
}

fun PriorityEnum?.toUiText(alternativeText: UiText? = null): UiText {
    return when (this) {
        PriorityEnum.LOW -> UiText.StringResourceId(id = R.string.priority_enum_text_low)
        PriorityEnum.NORMAL -> UiText.StringResourceId(id = R.string.priority_enum_text_normal)
        PriorityEnum.HIGH -> UiText.StringResourceId(id = R.string.priority_enum_text_high)
        else -> alternativeText
            ?: UiText.StringResourceId(id = R.string.priority_enum_text_no_selection)
    }

}

fun PriorityEnum?.uiTextLong(alternativeText: UiText? = null): UiText {
    return when (this) {
        PriorityEnum.HIGH -> UiText.StringResourceId(R.string.priority_enum_text_high_long)
        PriorityEnum.NORMAL -> UiText.StringResourceId(R.string.priority_enum_text_normal_long)
        PriorityEnum.LOW -> UiText.StringResourceId(R.string.priority_enum_text_low_long)
        null -> alternativeText ?: UiText.StringResourceId(R.string.priority_enum_text_no_selection)
    }
}

@Composable
fun PriorityEnum?.backgroundColor(default: Color): Color {
    return when (this) {
        PriorityEnum.HIGH -> MaterialTheme.colorScheme.priorityHigh
        PriorityEnum.NORMAL -> MaterialTheme.colorScheme.priorityNormal
        PriorityEnum.LOW -> MaterialTheme.colorScheme.priorityLow
        else -> default
    }
}


fun PriorityEnum?.iconContentDescription(): UiText {
    return when (this) {
        PriorityEnum.HIGH -> UiText.StringResourceId(R.string.priority_enum_high_icon_content_description)

        PriorityEnum.NORMAL -> UiText.StringResourceId(id = R.string.priority_enum_normal_icon_content_description)

        PriorityEnum.LOW -> UiText.StringResourceId(id = R.string.priority_enum_low_icon_content_description)
        else -> UiText.StringResourceId(id = R.string.priority_enum_no_selection_icon_content_description)
    }
}