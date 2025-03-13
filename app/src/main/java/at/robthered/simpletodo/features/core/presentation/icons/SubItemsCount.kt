package at.robthered.simpletodo.features.core.presentation.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val SubItemsCount: ImageVector
    get() {
        if (_SubItemsCount != null) {
            return _SubItemsCount!!
        }
        _SubItemsCount = ImageVector.Builder(
            name = "SubItemsCount",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 1f
            ) {
                moveTo(6.5f, 10f)
                curveTo(8.433f, 10f, 10f, 8.433f, 10f, 6.5f)
                curveTo(10f, 4.567f, 8.433f, 3f, 6.5f, 3f)
                curveTo(4.567f, 3f, 3f, 4.567f, 3f, 6.5f)
                curveTo(3f, 8.433f, 4.567f, 10f, 6.5f, 10f)
                close()
                moveTo(6.5f, 10f)
                verticalLineTo(12.5f)
                curveTo(6.5f, 15.261f, 8.739f, 17.5f, 11.5f, 17.5f)
                horizontalLineTo(14f)
                moveTo(14f, 17.5f)
                curveTo(14f, 19.433f, 15.567f, 21f, 17.5f, 21f)
                curveTo(19.433f, 21f, 21f, 19.433f, 21f, 17.5f)
                curveTo(21f, 15.567f, 19.433f, 14f, 17.5f, 14f)
                curveTo(15.567f, 14f, 14f, 15.567f, 14f, 17.5f)
                close()
            }
        }.build()

        return _SubItemsCount!!
    }

@Suppress("ObjectPropertyName")
private var _SubItemsCount: ImageVector? = null