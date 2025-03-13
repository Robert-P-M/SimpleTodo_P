package at.robthered.simpletodo.features.core.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate

@Composable
fun RowScope.AppToggleIcon(
    isIconVisible: Boolean,
    rotateLeft: Boolean,
    onToggle: () -> Unit,
) {
    AnimatedVisibility(
        visible = isIconVisible
    ) {
        val rotation by animateFloatAsState(
            targetValue = if (rotateLeft) 0f else 90f,
            label = "chevron rotation"
        )
        IconButton(
            onClick = onToggle
        ) {
            Icon(
                modifier = Modifier
                    .rotate(rotation),
                imageVector = Icons.Outlined.KeyboardArrowDown,
                contentDescription = "Expand/Collapse section icon"
            )
        }
    }
}