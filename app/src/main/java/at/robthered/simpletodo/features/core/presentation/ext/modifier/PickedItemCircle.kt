package at.robthered.simpletodo.features.core.presentation.ext.modifier

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun Modifier.pickedItemCircle() = drawBehind {
    drawCircle(
        color = Color.Blue,
        style = Stroke(width = 12f),
        alpha = 0.5f,
        radius = size.minDimension / 2
    )
}