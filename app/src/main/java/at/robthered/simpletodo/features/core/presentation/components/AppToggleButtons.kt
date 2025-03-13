package at.robthered.simpletodo.features.core.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.presentation.UiText
import at.robthered.simpletodo.features.dataSource.presentation.model.getText
import at.robthered.simpletodo.features.dataSource.domain.local.model.EventModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityEnum
import at.robthered.simpletodo.features.dataSource.presentation.model.icon
import at.robthered.simpletodo.features.dataSource.presentation.model.toUiText

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun PriorityToggleButton(
    modifier: Modifier = Modifier,
    priorityEnum: PriorityEnum?,
    onCheckedChanged: (Boolean) -> Unit,
) {
    ToggleButton(
        modifier = modifier,
        checked = priorityEnum != null,
        onCheckedChange = onCheckedChanged
    ) {
        Icon(
            imageVector = priorityEnum.icon(),
            contentDescription = null,
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = priorityEnum.toUiText(
                alternativeText = UiText.StringResourceId(id = R.string.task_priority_label)
            ).asString()
        )

    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun EventToggleButton(
    modifier: Modifier = Modifier,
    taskEventModel: EventModel?,
    onCheckedChanged: (Boolean) -> Unit,
) {
    ToggleButton(
        modifier = modifier,
        checked = taskEventModel?.isActive == true,
        onCheckedChange = onCheckedChanged
    ) {
        Icon(
            imageVector = Icons.Outlined.CalendarToday,
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = taskEventModel?.getText()
                ?: stringResource(
                    R.string.due_dialog_title
                )
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun NotificationToggleButton(
    modifier: Modifier = Modifier,
    taskEventModel: EventModel?,
    onCheckedChanged: (Boolean) -> Unit,
) {
    ToggleButton(
        modifier = modifier,
        checked = taskEventModel?.isNotificationEnabled == true,
        enabled = taskEventModel?.isActive == true,
        onCheckedChange = onCheckedChanged
    ) {
        Icon(
            imageVector = Icons.Outlined.Alarm,
            contentDescription = null,
            modifier =
            Modifier
                .size(ToggleButtonDefaults.IconSize)
                .wrapContentSize(align = Alignment.Center),
        )
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = stringResource(R.string.toggle_button_notification)
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AddLinkToggleButton(
    modifier: Modifier = Modifier,
    count: Int,
    onCheckedChanged: (Boolean) -> Unit,
) {
    ToggleButton(
        modifier = modifier,
        checked = count > 0,
        onCheckedChange = onCheckedChanged
    ) {
        Icon(
            imageVector = Icons.Outlined.Link,
            contentDescription = null,
            modifier =
            Modifier
                .size(ToggleButtonDefaults.IconSize)
                .wrapContentSize(align = Alignment.Center),
        )
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = pluralStringResource(
                R.plurals.toggle_button_add_link,
                count = count,
                formatArgs = arrayOf(count)
            )
        )
    }
}