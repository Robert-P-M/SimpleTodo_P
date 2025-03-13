package at.robthered.simpletodo.features.taskActivityProtocolDialog.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import at.robthered.simpletodo.features.core.presentation.ext.modifier.borderBottom
import at.robthered.simpletodo.features.core.utils.ext.toFormattedDateTime
import kotlinx.datetime.Instant

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TaskActivityItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    iconTint: Color = LocalContentColor.current,
    createdAt: Instant,
    content: @Composable ColumnScope.() -> Unit
) {
    val strokeColor = MaterialTheme.colorScheme.onSurfaceVariant
        .copy(alpha = 0.3f)
    Row(
        modifier = modifier
            .borderBottom(strokeColor)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(
            space = 16.dp
        ),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            modifier = Modifier
                .size(ToggleButtonDefaults.IconSize)
                .wrapContentSize(align = Alignment.Center),
            imageVector = icon,
            contentDescription = null,
            tint = iconTint
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            content()
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(ToggleButtonDefaults.IconSize)
                        .wrapContentSize(align = Alignment.Center),
                    imageVector = Icons.Outlined.CalendarToday,
                    contentDescription = null
                )
                Text(
                    text = createdAt.toFormattedDateTime(),
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}