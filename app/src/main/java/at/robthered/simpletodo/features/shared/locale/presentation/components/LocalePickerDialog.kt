package at.robthered.simpletodo.features.shared.locale.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import at.robthered.simpletodo.features.core.presentation.components.AppDialog
import at.robthered.simpletodo.features.shared.locale.presentation.components.state.LocalePickerState
import at.robthered.simpletodo.features.shared.locale.presentation.ext.getResId
import at.robthered.simpletodo.features.shared.locale.presentation.ext.toUiText
import at.robthered.simpletodo.features.shared.locale.presentation.model.AppLocaleUi

@Composable
fun LocalePickerDialog(
    localePickerState: LocalePickerState
) {
    AnimatedVisibility(
        visible = localePickerState.isVisible,
    ) {
        AppDialog(
            onDismissRequest = {
                localePickerState.onHide()
            }
        ) {
            val availableLocales = remember {
                localePickerState.availableLocales
            }
            val listState = rememberLazyListState()
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 400.dp)
            ) {
                items(items = availableLocales) { appLocaleUi: AppLocaleUi ->
                    Box(modifier = Modifier
                        .clickable(true) {
                            localePickerState.onSaveLocale(appLocaleUi)
                        }
                        .fillMaxWidth()
                        .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(appLocaleUi.getResId()),
                                contentDescription = null
                            )
                            Text(
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Start,
                                text = appLocaleUi.toUiText().asString(),
                                style = MaterialTheme.typography.titleSmall,
                            )
                            if (appLocaleUi.language == localePickerState.currentLocale.language) {
                                Icon(
                                    imageVector = Icons.Outlined.Check,
                                    contentDescription = "Selected item checkmark",
                                    tint = Color.Green.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}