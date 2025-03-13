package at.robthered.simpletodo.features.core.presentation.icons


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val EventStart: ImageVector
    get() {
        if (_EventStart != null) {
            return _EventStart!!
        }
        _EventStart = ImageVector.Builder(
            name = "EventStart",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFF000000))) {
                moveTo(16f, 18f)
                lineTo(14.575f, 16.6f)
                lineTo(18.175f, 13f)
                horizontalLineTo(6f)
                verticalLineTo(11f)
                horizontalLineTo(18.175f)
                lineTo(14.6f, 7.4f)
                lineTo(16f, 6f)
                lineTo(22f, 12f)
                lineTo(16f, 18f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000))) {
                moveTo(2f, 6f)
                verticalLineTo(18f)
                horizontalLineTo(4f)
                verticalLineTo(6f)
                horizontalLineTo(2f)
                close()
            }
        }.build()

        return _EventStart!!
    }

@Suppress("ObjectPropertyName")
private var _EventStart: ImageVector? = null