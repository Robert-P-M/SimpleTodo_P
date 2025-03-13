package at.robthered.simpletodo.features.shared.locale.domain.model

open class AppLocale(val language: String, val country: String) {
    override fun equals(other: Any?): Boolean {
        return other is AppLocale && other.language == language && other.country == country
    }

    override fun hashCode(): Int {
        return language.hashCode() * 31 + country.hashCode()
    }

    override fun toString(): String {
        return "AppLocale(language=$language, country=$country)"
    }
}