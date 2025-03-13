package at.robthered.simpletodo.features.dataSource.presentation.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.NotInterested
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.NotificationsOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.presentation.UiText
import at.robthered.simpletodo.features.core.presentation.theme.completedColor
import at.robthered.simpletodo.features.core.presentation.theme.unCompletedColor
import at.robthered.simpletodo.features.core.utils.ext.toFormat
import at.robthered.simpletodo.features.core.utils.ext.toFormattedDate
import at.robthered.simpletodo.features.core.utils.ext.toFormattedDateTime
import at.robthered.simpletodo.features.core.utils.ext.toLocalDate
import at.robthered.simpletodo.features.core.utils.ext.toLocalTime
import at.robthered.simpletodo.features.dataSource.presentation.model.completed
import at.robthered.simpletodo.features.dataSource.domain.local.model.EventModel
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun EventModel.activityIcon(): ImageVector {
    return when (this.isActive) {
        true -> Icons.Outlined.Event
        false -> Icons.Outlined.NotInterested
    }
}


fun EventModel.activityText(): UiText {
    return if(this.isActive) {

        return when(this.isWithTime) {
            true -> UiText.StringResourceId(id = R.string.task_activity_dialog_event_activity_text_is_active_date_time, arrayOf(this.start.toLocalDate().toFormat(), this.start.toLocalDateTime(timeZone = TimeZone.currentSystemDefault()).time.toString()))
            false -> UiText.StringResourceId(id = R.string.task_activity_dialog_event_activity_text_is_active_date, arrayOf(this.start.toLocalDate().toFormat()))
        }
    } else {
        UiText.StringResourceId(id = R.string.task_activity_dialog_event_activity_no_event)
    }
}

fun EventModel.activityNotificationText(): UiText? {
    return if(this.isActive){
        when(this.isNotificationEnabled) {
            true -> UiText.StringResourceId(id = R.string.task_activity_dialog_event_activity_notification_enabled)
            false -> UiText.StringResourceId(id = R.string.task_activity_dialog_event_activity_notification_disabled)
        }
    } else null
}

fun EventModel.activityNotificationIcon(): ImageVector {
    return when(this.isNotificationEnabled) {
        true -> Icons.Outlined.Notifications
        false -> Icons.Outlined.NotificationsOff
    }
}

@Composable
fun EventModel.activityNotificationIconTint(): Color {
    return when(this.isNotificationEnabled) {
        true -> MaterialTheme.colorScheme.onTertiaryContainer
        false -> MaterialTheme.colorScheme.error.copy(alpha = 0.7f)
    }
}

@Composable
fun EventModel?.contentColor(): Color {
    return if(completed()){
        MaterialTheme.colorScheme.completedColor
    } else {
        MaterialTheme.colorScheme.unCompletedColor
    }
}

fun EventModel.getFormattedDateTime(): String? {
    return if (isWithTime) start.toFormattedDateTime() else null
}

fun EventModel.getFormattedTime(): LocalTime? {
    return if (isWithTime) this.start.toLocalDateTime(TimeZone.currentSystemDefault()).time else null
}

fun EventModel.getDurationLocalTime(): LocalTime? {
    return durationMinutes?.toLocalTime()
}

fun EventModel?.isDurationToggleEnabled(): Boolean {
    return this != null && isWithTime
}

@Composable
fun EventModel?.getText(): String? {

    val text =  this?.let { event ->
        if (event.isWithTime) {
            event.end?.let { end ->
                "${event.start.toFormattedDateTime()} - ${end.toFormattedDateTime()}"
            } ?: event.start.toFormattedDateTime()
        } else event.start.toFormattedDate()
    }
    return if(this?.isActive == true) {
        text

    } else null

}

fun EventModel?.completed(): Boolean {
    return this?.isCompleted ?: false
}