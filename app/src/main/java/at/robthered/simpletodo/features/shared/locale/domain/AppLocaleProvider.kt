package at.robthered.simpletodo.features.shared.locale.domain

import at.robthered.simpletodo.features.shared.locale.domain.model.AppLocale

interface AppLocaleProvider {
    fun getLocale(): AppLocale
    fun setLocale(appLocale: AppLocale)
}