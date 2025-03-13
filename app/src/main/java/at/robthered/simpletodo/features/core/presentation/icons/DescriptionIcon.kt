package at.robthered.simpletodo.features.core.presentation.icons


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val DescriptionIcon: ImageVector
    get() {
        if (_DescriptionIcon != null) {
            return _DescriptionIcon!!
        }
        _DescriptionIcon = ImageVector.Builder(
            name = "DescriptionIcon",
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
                moveTo(3f, 16f)
                curveTo(3.4f, 16f, 15.167f, 16f, 21f, 16f)
            }
            path(
                stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 1f,
                strokeLineCap = StrokeCap.Round
            ) {
                moveTo(3f, 20f)
                curveTo(3.267f, 20f, 11.111f, 20f, 15f, 20f)
            }
            path(
                stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 1f,
                strokeLineCap = StrokeCap.Round
            ) {
                moveTo(3f, 12f)
                horizontalLineTo(21f)
            }
            path(
                stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 1f,
                strokeLineCap = StrokeCap.Round
            ) {
                moveTo(3f, 8f)
                horizontalLineTo(21f)
            }
            path(
                stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 1f,
                strokeLineCap = StrokeCap.Round
            ) {
                moveTo(3f, 4f)
                horizontalLineTo(21f)
            }
        }.build()

        return _DescriptionIcon!!
    }

@Suppress("ObjectPropertyName")
private var _DescriptionIcon: ImageVector? = null