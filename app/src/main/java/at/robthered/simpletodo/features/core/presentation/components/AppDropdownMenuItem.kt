package at.robthered.simpletodo.features.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.presentation.UiText
import at.robthered.simpletodo.features.core.presentation.ext.modifier.borderBottom
import at.robthered.simpletodo.features.core.presentation.uiModel.MenuItem


@Composable
fun AppDropdownMenuItem(
    modifier: Modifier = Modifier,
    menuItem: MenuItem,
) {
    when (menuItem) {
        is MenuItem.Action -> AppDropdownMenuItem(menuItem = menuItem, modifier = modifier)
        is MenuItem.Default -> AppDropdownMenuItem(menuItem = menuItem, modifier = modifier)
        is MenuItem.Header -> AppDropdownMenuItem(menuItem = menuItem, modifier = modifier)
        is MenuItem.Close -> AppDropdownMenuItem(menuItem = menuItem, modifier = modifier)
    }
}

@Composable
fun AppDropdownMenuItem(
    menuItem: MenuItem.Default,
    modifier: Modifier = Modifier,
) {
    DropdownMenuItem(
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
        modifier = modifier,
        text = {
            Text(
                text = menuItem.text.asString(),
                textAlign = TextAlign.End
            )
        },
        leadingIcon = {
            menuItem.leadingIcon?.let { imageVector ->
                Icon(
                    imageVector = imageVector,
                    contentDescription = menuItem.iconDescription?.asString()
                )
            }
        },
        onClick = { menuItem.onClick() }
    )
}

@Composable
fun AppDropdownMenuItem(
    menuItem: MenuItem.Header,
    modifier: Modifier = Modifier,
) {
    DropdownMenuItem(
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
        modifier = modifier
            .borderBottom(MaterialTheme.colorScheme.onSurfaceVariant),
        text = {
            Text(
                text = menuItem.title.asString()
            )
        },
        onClick = {

        }
    )
}

@Composable
fun AppDropdownMenuItem(
    menuItem: MenuItem.Action,
    modifier: Modifier = Modifier,
) {
    DropdownMenuItem(
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
        modifier = modifier.then(
            if (menuItem.backgroundColor != null) {
                Modifier.background(menuItem.backgroundColor)
            } else Modifier
        ),
        text = {
            Text(
                text = menuItem.text.asString(),
                color = menuItem.textColor ?: MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.End
            )
        },
        leadingIcon = {
            menuItem.leadingIcon?.let { imageVector ->
                Icon(
                    imageVector = imageVector,
                    contentDescription = menuItem.iconDescription?.asString(),
                    tint = menuItem.iconTint ?: MaterialTheme.colorScheme.surfaceTint
                )
            }
        },
        onClick = menuItem.onClick
    )
}

/**
 *
 *                 text = UiText.StringResourceId(R.string.dropdown_menu_item_text_close),
 *                 leadingIcon = Icons.Outlined.Close,
 *                 iconDescription = UiText.StringResourceId(R.string.dropdown_menu_item_icon_description_close),
 */

@Composable
fun AppDropdownMenuItem(
    menuItem: MenuItem.Close,
    modifier: Modifier = Modifier,
) {
    DropdownMenuItem(
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
        modifier = modifier,
        text = {
            Text(
                text = UiText.StringResourceId(R.string.dropdown_menu_item_text_close).asString(),
                textAlign = TextAlign.End
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = UiText.StringResourceId(R.string.dropdown_menu_item_icon_description_close)
                    .asString(),
            )
        },
        onClick = menuItem.onClick
    )
}