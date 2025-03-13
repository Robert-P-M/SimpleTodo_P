package at.robthered.simpletodo.features.shared.locale.domain

interface AppLocaleManager {
    fun changeLanguage(languageCode: String)
    fun getLanguageCode(): String
}