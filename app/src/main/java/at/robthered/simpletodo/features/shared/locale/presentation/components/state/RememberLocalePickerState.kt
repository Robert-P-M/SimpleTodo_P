package at.robthered.simpletodo.features.shared.locale.presentation.components.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import at.robthered.simpletodo.features.shared.locale.domain.model.AppLocale

@Composable
fun rememberLocalePickerState(
    currentLocale: AppLocale,
    onSave: ( appLocale: AppLocale) -> Unit,
): LocalePickerState {
    return remember {
        LocalePickerState(
            currentLocale = currentLocale,
            onSave = onSave,
        )
    }
}