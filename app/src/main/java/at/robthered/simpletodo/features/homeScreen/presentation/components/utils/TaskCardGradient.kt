package at.robthered.simpletodo.features.homeScreen.presentation.components.utils

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityEnum
import at.robthered.simpletodo.features.dataSource.presentation.model.backgroundColor

@Composable
fun priorityCardGradient(
    priorityEnum: PriorityEnum?,
    defaultBackgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
): ShaderBrush {
    val priorityColor by animateColorAsState(
        targetValue = priorityEnum
            .backgroundColor(default = defaultBackgroundColor),
        animationSpec = tween(durationMillis = 300)
    )

    val transition = remember { Animatable(1f) }
    LaunchedEffect(
        Unit,
        priorityColor,
    ) { transition.animateTo(2f) }

    return remember(priorityColor, transition.value) {
        object : ShaderBrush() {
            override fun createShader(size: Size): Shader {

                return RadialGradientShader(
                    center = Offset(
                        x = -(size.width * 2.3f),
                        y = -(size.width * 1.8f)
                    ),
                    radius = size.width * 4f,
                    colorStops = listOf(0f, 0.8f, 1f),
                    colors = listOf(
                        defaultBackgroundColor.copy(alpha = 0.5f),
                        defaultBackgroundColor.copy(alpha = 0.5f),
                        priorityColor.copy(alpha = 0.3f)
                    ),
                    tileMode = TileMode.Clamp
                )
            }
        }
    }
}