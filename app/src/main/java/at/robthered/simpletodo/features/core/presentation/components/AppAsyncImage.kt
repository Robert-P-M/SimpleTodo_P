package at.robthered.simpletodo.features.core.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest

@Composable
fun AppAsyncImage(
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    model: String?,
) {
    val context = LocalContext.current

    AsyncImage(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onTertiaryContainer.copy(
                    alpha = 0.3f
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .clip(shape = RoundedCornerShape(8.dp))
            .shadow(elevation = 1.dp),
        model = ImageRequest.Builder(context)
            .data(model).build(),
        contentDescription = contentDescription,
    )
}