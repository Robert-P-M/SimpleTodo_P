package at.robthered.simpletodo.features.core.presentation.ext.modifier

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color

@Composable
fun Modifier.todayItemCircle(divider: Int = 2) = drawBehind {
    drawCircle(
        color = Color.Red.copy(alpha = 0.5f),
        radius = size.minDimension.div(divider),
    )
}