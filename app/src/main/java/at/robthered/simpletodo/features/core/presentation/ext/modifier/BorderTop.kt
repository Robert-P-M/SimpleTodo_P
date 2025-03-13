package at.robthered.simpletodo.features.core.presentation.ext.modifier

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

@Composable
fun Modifier.borderTop(strokeColor: Color) = drawBehind {
    drawLine(
        start = Offset(
            0f, 0f
        ),
        end = Offset(
            size.width,
            0f
        ),
        strokeWidth = 2f,
        color = strokeColor
    )
}