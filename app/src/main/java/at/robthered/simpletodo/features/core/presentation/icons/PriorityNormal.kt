package at.robthered.simpletodo.features.core.presentation.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val PriorityNormal: ImageVector
    get() {
        if (_PriorityNormal != null) {
            return _PriorityNormal!!
        }
        _PriorityNormal = ImageVector.Builder(
            name = "PriorityNormal",
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
                moveTo(2f, 12f)
                lineTo(12f, 12f)
                lineTo(22f, 12f)
            }
        }.build()

        return _PriorityNormal!!
    }

@Suppress("ObjectPropertyName")
private var _PriorityNormal: ImageVector? = null