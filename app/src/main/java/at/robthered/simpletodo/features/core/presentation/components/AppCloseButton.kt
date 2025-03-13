package at.robthered.simpletodo.features.core.presentation.components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun BoxScope.AppCloseButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = modifier.align(Alignment.CenterEnd),
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Outlined.Close,
            contentDescription = null
        )
    }
}