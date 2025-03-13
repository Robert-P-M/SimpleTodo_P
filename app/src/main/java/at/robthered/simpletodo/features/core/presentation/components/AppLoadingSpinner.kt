package at.robthered.simpletodo.features.core.presentation.components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BoxScope.AppLoadingSpinner(
    modifier: Modifier = Modifier,
) {
    CircularProgressIndicator(
        modifier = modifier.align(Alignment.CenterStart).size(18.dp)
    )
}