package at.robthered.simpletodo.features.shared.locale.domain.state

import at.robthered.simpletodo.features.shared.locale.domain.model.AppLocale

sealed interface AppLocaleStateAction {
    data class OnPickAppLocale(val appLocale: AppLocale): AppLocaleStateAction
}