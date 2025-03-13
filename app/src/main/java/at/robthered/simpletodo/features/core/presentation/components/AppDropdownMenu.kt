package at.robthered.simpletodo.features.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import at.robthered.simpletodo.features.core.presentation.ext.modifier.crop

@Composable
fun AppDropdownMenu(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    offset: DpOffset? = null,
    content: @Composable() (ColumnScope.() -> Unit),
) {
    DropdownMenu(
        modifier = modifier
            .crop(vertical = 8.dp),
        properties = PopupProperties(
            dismissOnBackPress = true,
        ),
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        offset = offset?.let {
            offset
        } ?: DpOffset(x = 0.dp, y = 0.dp),
        border = BorderStroke(
            1.dp,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.05f)
        ),
        shadowElevation = 4.dp
    ) {
        content()
    }
}