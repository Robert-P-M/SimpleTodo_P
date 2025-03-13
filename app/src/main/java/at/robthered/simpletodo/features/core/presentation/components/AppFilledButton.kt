package at.robthered.simpletodo.features.core.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppFilledButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit,
    text: String,
) {
    val buttonAlpha by
    animateFloatAsState(
        targetValue = if (enabled) 1f else 0.4f,
        label = "button alpha",
        animationSpec = tween(
            durationMillis = 300
        )
    )

    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = ButtonDefaults.filledTonalButtonColors().containerColor.copy(
                alpha = buttonAlpha
            )
        )
    ) {
        Text(
            text = text
        )
    }
}