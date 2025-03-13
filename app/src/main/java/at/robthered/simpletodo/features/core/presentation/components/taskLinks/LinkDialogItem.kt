package at.robthered.simpletodo.features.core.presentation.components.taskLinks

import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.presentation.components.AppAsyncImage
import at.robthered.simpletodo.features.core.presentation.components.AppFilledButton
import at.robthered.simpletodo.features.dataSource.domain.local.model.LinkModel
import at.robthered.simpletodo.features.network.domain.model.SharedUrlModel

@Composable
fun LinkDialogItem(
    modifier: Modifier = Modifier,
    new: Boolean? = false,
    index: Int,
    item: SharedUrlModel,
    onDeleteItem: (link: String) -> Unit
) {
    Column(
        modifier = modifier.padding(
            horizontal = 16.dp,
            vertical = 8.dp,
        ),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = new?.let { "${index + 1}. (new)" } ?: "${index + 1}"

                ,
                fontWeight = FontWeight.SemiBold
            )
            Icon(
                imageVector = Icons.Outlined.Delete,
                tint = MaterialTheme.colorScheme.error.copy(alpha = 0.7f),
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        onDeleteItem(item.link)
                    }
            )
        }
        Text(
            text = item.title,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = item.description ?: "No description",
            style = MaterialTheme.typography.bodyMedium
        )
        AppAsyncImage(
            model = item.imageUrl,
            contentDescription = null,
        )
    }
    HorizontalDivider(
        thickness = 6.dp,
        modifier = Modifier.padding(vertical = 2.dp)
    )
}

@Composable
fun LinkDialogItem(
    modifier: Modifier = Modifier,
    index: Int,
    item: LinkModel,
    onDeleteItem: (linkId: Long?) -> Unit,
) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}
    Column(
        modifier = modifier.padding(
            horizontal = 16.dp,
            vertical = 8.dp,
        ),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${index + 1}.",
                fontWeight = FontWeight.SemiBold
            )
            Icon(
                imageVector = Icons.Outlined.Delete,
                tint = MaterialTheme.colorScheme.error.copy(alpha = 0.7f),
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        onDeleteItem(item.linkId)
                    }
            )
        }
        Text(
            text = item.title,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = item.description ?: "No description",
            style = MaterialTheme.typography.bodyMedium
        )
        AppAsyncImage(
            model = item.imageFilePath,
            contentDescription = null,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val linkIntent = remember { Intent(Intent.ACTION_VIEW, item.linkUrl.toUri()) }
            AppFilledButton(
                text = stringResource(R.string.open_link),
                enabled = true,
                onClick = {
                    launcher.launch(linkIntent)
                }
            )
        }
    }
    HorizontalDivider(
        thickness = 6.dp,
        modifier = Modifier.padding(vertical = 2.dp)
    )
}