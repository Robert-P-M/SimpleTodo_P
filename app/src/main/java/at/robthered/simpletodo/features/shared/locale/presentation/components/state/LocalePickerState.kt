package at.robthered.simpletodo.features.shared.locale.presentation.components.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import at.robthered.simpletodo.features.shared.locale.domain.model.AppLocale
import at.robthered.simpletodo.features.shared.locale.presentation.model.AppLocaleUi

class LocalePickerState(
    val currentLocale: AppLocale,
    private val onSave: (appLocale: AppLocale) -> Unit,
) {
    val availableLocales =
        listOf(
            AppLocaleUi.DeDE,
            AppLocaleUi.UsEN,
        )
    var isVisible by mutableStateOf(false)
        private set

    fun onShow(){
        isVisible = true
    }
    fun onHide() {
        isVisible = false
    }
    fun isPicked(appLocale: AppLocale): Boolean {
        return currentLocale == appLocale
    }
    fun onSaveLocale(appLocale: AppLocale) {
        isVisible = false
        onSave(appLocale)
    }
}