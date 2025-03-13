package at.robthered.simpletodo.features.shared.locale.domain.state

import at.robthered.simpletodo.features.shared.locale.domain.model.AppLocale

data class AppLocaleState(
    val appLocale: AppLocale = AppLocale(language = "de", "DE")
)