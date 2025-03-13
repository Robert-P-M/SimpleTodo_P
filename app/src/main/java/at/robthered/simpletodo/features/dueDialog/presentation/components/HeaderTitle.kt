package at.robthered.simpletodo.features.dueDialog.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ColumnScope.HeaderTitle(
    currentMonthYear: String?,
    scrollUp: () -> Unit,
) {
    AnimatedVisibility(
        visible = currentMonthYear != null
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        ) {
            IconButton(
                modifier = Modifier.align(Alignment.CenterEnd),
                onClick = scrollUp
            ) {
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowUp,
                    contentDescription = null
                )
            }
            MonthTitle(
                text = currentMonthYear.orEmpty()
            )
        }
    }
}