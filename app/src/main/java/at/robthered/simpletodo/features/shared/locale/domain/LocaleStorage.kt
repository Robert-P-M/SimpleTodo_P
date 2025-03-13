package at.robthered.simpletodo.features.shared.locale.domain

import at.robthered.simpletodo.features.shared.locale.domain.model.AppLocale

interface LocaleStorage {
    fun saveLocale(appLocale: AppLocale)
    fun getLocale(): AppLocale
}