package at.robthered.simpletodo.features.shared.locale.data

import at.robthered.simpletodo.features.shared.locale.domain.AppLocaleManager
import at.robthered.simpletodo.features.shared.locale.domain.AppLocaleProvider
import at.robthered.simpletodo.features.shared.locale.domain.LocaleStorage
import at.robthered.simpletodo.features.shared.locale.domain.model.AppLocale

class AppLocaleProviderImpl(
    private val localeStorage: LocaleStorage,
    private val appLocaleManager: AppLocaleManager,
): AppLocaleProvider {
    override fun getLocale(): AppLocale {
        val locale =  localeStorage.getLocale()
        appLocaleManager.changeLanguage(locale.language)
        return locale
    }
    override fun setLocale(appLocale: AppLocale) {
        appLocaleManager.changeLanguage(appLocale.language)
        localeStorage.saveLocale(appLocale)
    }
}