package at.robthered.simpletodo.features.shared.locale.data.mapper

import at.robthered.simpletodo.features.shared.locale.domain.model.AppLocale
import java.util.Locale

fun Locale.toAppLocale(): AppLocale {
    return AppLocale(
        language = this.language,
        country = this.country
    )
}

fun AppLocale.toLocale(): Locale {
    return Locale(
        this.language,
        this.country,
    )
}