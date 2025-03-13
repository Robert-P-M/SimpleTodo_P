package at.robthered.simpletodo.features.core.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val ColorScheme.priorityLow: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF81C784) else Color(0xFF638564)

val ColorScheme.priorityNormal: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFFFFF176) else Color(0xFFA49D5C)

val ColorScheme.priorityHigh: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFFE57373) else Color(0xFF935E5E)

val ColorScheme.completedColor: Color
    @Composable
    get() = if(isSystemInDarkTheme()) Color(0xFF81C784) else Color(0xFF4CAF50)

val ColorScheme.unCompletedColor: Color
    @Composable
    get() = if(isSystemInDarkTheme()) Color(0xFFE57373) else Color(0xFFF44336)