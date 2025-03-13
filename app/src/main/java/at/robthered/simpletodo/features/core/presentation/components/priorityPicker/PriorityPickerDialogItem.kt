package at.robthered.simpletodo.features.core.presentation.components.priorityPicker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityEnum
import at.robthered.simpletodo.features.core.presentation.UiText
import at.robthered.simpletodo.features.core.presentation.icons.PriorityPicker
import at.robthered.simpletodo.features.core.presentation.uiModel.PriorityItem
import at.robthered.simpletodo.features.dataSource.presentation.model.iconTint


/**
 * Dialog Item for header
 */
@Composable
fun PriorityPickerDialogItem(
    modifier: Modifier,
    onDismissRequest: () -> Unit,
) {
    Box(modifier = modifier
        .clickable(true) {
            onDismissRequest()
        }
        .fillMaxWidth()
        .padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = PriorityPicker,
                contentDescription = null
            )
            Text(
                text = stringResource(R.string.priority_enum_text_no_selection),
                style = MaterialTheme.typography.titleSmall,
            )

            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = "Selected item checkmark",
            )

        }
    }
}

/**
 * Dialog Item for priorities
 */
@Composable
fun PriorityPickerDialogItem(
    modifier: Modifier,
    onPickPriority: (priority: PriorityEnum?) -> Unit,
    item: PriorityItem,
    currentPriority: PriorityEnum?,
) {
    Box(modifier = modifier
        .clickable(true) {
            onPickPriority(item.priority)

        }
        .fillMaxWidth()
        .padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = item.leadingIcon,
                contentDescription = null,
                tint = item.priority.iconTint()
            )
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start,
                text = item.text.asString(),
                style = MaterialTheme.typography.titleSmall,
                color = item.priority.iconTint()
            )
            if (item.priority == currentPriority) {
                Icon(
                    imageVector = Icons.Outlined.Check,
                    contentDescription = "Selected item checkmark",
                    tint = Color.Green.copy(alpha = 0.7f)
                )
            }
        }
    }
}

/**
 * Dialog Item for reset priority
 */
@Composable
fun PriorityPickerDialogItem(
    modifier: Modifier,
    onPickPriority: (priority: PriorityEnum?) -> Unit,
) {
    Box(modifier = modifier
        .clickable(true) {
            onPickPriority(null)
        }
        .fillMaxWidth()
        .padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error.copy(0.8f)
            )
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start,
                text = UiText.StringResourceId(R.string.priority_enum_text_remove).asString(),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.error.copy(0.8f)
            )
        }
    }
}