package at.robthered.simpletodo.features.shared.locale.data

import android.content.SharedPreferences
import at.robthered.simpletodo.features.shared.locale.domain.LocaleStorage
import at.robthered.simpletodo.features.shared.locale.domain.model.AppLocale
import java.util.Locale
import androidx.core.content.edit

class SharedPreferencesLocaleStorage(
    private val sharedPreferences: SharedPreferences,
): LocaleStorage {
    override fun saveLocale(appLocale: AppLocale) {
        sharedPreferences.edit() {
            putString(LANGUAGE, appLocale.language)
                .putString(COUNTRY, appLocale.country)
        }
    }

    override fun getLocale(): AppLocale {
        val language = sharedPreferences.getString(LANGUAGE, Locale.getDefault().language) ?: Locale.getDefault().language
        val country = sharedPreferences.getString(COUNTRY, Locale.getDefault().country) ?: Locale.getDefault().country
        return AppLocale(language, country)
    }
    companion object {
        const val LANGUAGE = "LANGUAGE"
        const val COUNTRY = "COUNTRY"
    }
}