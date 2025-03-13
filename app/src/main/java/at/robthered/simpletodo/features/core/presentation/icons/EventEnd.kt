package at.robthered.simpletodo.features.core.presentation.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val EventEnd: ImageVector
    get() {
        if (_EventEnd != null) {
            return _EventEnd!!
        }
        _EventEnd = ImageVector.Builder(
            name = "EventEnd",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFF000000))) {
                moveTo(12f, 18f)
                lineTo(10.575f, 16.6f)
                lineTo(14.175f, 13f)
                horizontalLineTo(2f)
                verticalLineTo(11f)
                horizontalLineTo(14.175f)
                lineTo(10.6f, 7.4f)
                lineTo(12f, 6f)
                lineTo(18f, 12f)
                lineTo(12f, 18f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000))) {
                moveTo(20f, 6f)
                verticalLineTo(18f)
                horizontalLineTo(22f)
                verticalLineTo(6f)
                horizontalLineTo(20f)
                close()
            }
        }.build()

        return _EventEnd!!
    }

@Suppress("ObjectPropertyName")
private var _EventEnd: ImageVector? = null