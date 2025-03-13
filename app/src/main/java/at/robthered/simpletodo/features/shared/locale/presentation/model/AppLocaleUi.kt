package at.robthered.simpletodo.features.shared.locale.presentation.model

import at.robthered.simpletodo.features.shared.locale.domain.model.AppLocale

sealed class AppLocaleUi(language: String, country: String): AppLocale(language, country) {
    data object UsEN : AppLocaleUi("en", "US")
    data object DeDE : AppLocaleUi("de", "DE")
}