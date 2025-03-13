package at.robthered.simpletodo.features.core.presentation.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import at.robthered.simpletodo.R

@Composable
fun AppMenuIconHorizontal(
    onClick: () -> Unit,
) {
    IconButton(
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(onTap = {})
            },
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Outlined.MoreHoriz,
            contentDescription = stringResource(R.string.icon_description_menu_icon_horizontal)
        )
    }
}