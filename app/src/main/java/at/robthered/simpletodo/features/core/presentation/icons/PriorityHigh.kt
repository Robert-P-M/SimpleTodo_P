package at.robthered.simpletodo.features.core.presentation.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val PriorityHigh: ImageVector
    get() {
        if (_PriorityHigh != null) {
            return _PriorityHigh!!
        }
        _PriorityHigh = ImageVector.Builder(
            name = "PriorityHigh",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 1f,
                strokeLineCap = StrokeCap.Round
            ) {
                moveTo(2f, 16f)
                lineTo(11.375f, 8.5f)
                curveTo(11.741f, 8.208f, 12.259f, 8.208f, 12.625f, 8.5f)
                lineTo(22f, 16f)
            }
        }.build()

        return _PriorityHigh!!
    }

@Suppress("ObjectPropertyName")
private var _PriorityHigh: ImageVector? = null