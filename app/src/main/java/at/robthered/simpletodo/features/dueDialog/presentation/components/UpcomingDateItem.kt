package at.robthered.simpletodo.features.dueDialog.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.EventAvailable
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import at.robthered.simpletodo.features.core.presentation.icons.EventEnd
import at.robthered.simpletodo.features.core.presentation.icons.EventStart
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppUpcomingDate
import at.robthered.simpletodo.features.shared.date_time.presentation.ext.getImageVector
import at.robthered.simpletodo.features.shared.date_time.presentation.ext.getTitleShort
import at.robthered.simpletodo.features.shared.date_time.presentation.ext.toUiText

@Composable
fun UpcomingDateItem(
    modifier: Modifier = Modifier,
    appUpcomingDate: AppUpcomingDate,
    onClick: () -> Unit,
) {
    CalendarRow(
        modifier = modifier
            .clickable {
                onClick()
            },
    ) {
        Icon(
            imageVector = appUpcomingDate.getImageVector(),
            contentDescription = null,
        )
        Text(
            text = appUpcomingDate.toUiText().asString(),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Start
        )
        Text(
            text = appUpcomingDate.getTitleShort()
        )
    }
}

@Composable
fun SelectedUpcomingStartItem(
    modifier: Modifier = Modifier,
    eventStart: String,
    isEndAvailable: Boolean,
    onClick: () -> Unit,
    withDelete: Boolean = true,
    alarmToggle: @Composable () -> Unit = {},
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = if (isEndAvailable) EventStart else Icons.Outlined.EventAvailable,
            contentDescription = null,
        )
        Text(
            text = eventStart,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Start
        )
        if(withDelete) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error.copy(alpha = 0.7f)
            )
        }
        alarmToggle()

    }
}

@Composable
fun SelectedUpcomingEndItem(
    modifier: Modifier = Modifier,
    eventEnd: String,
    onClick: () -> Unit,
    withDelete: Boolean = true
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = EventEnd,
            contentDescription = null,
        )
        Text(
            text = eventEnd,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Start
        )
        if(withDelete) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error.copy(alpha = 0.7f)
            )
        }
    }
}