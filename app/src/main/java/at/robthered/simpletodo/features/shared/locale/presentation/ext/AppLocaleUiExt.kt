package at.robthered.simpletodo.features.shared.locale.presentation.ext

import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.presentation.UiText
import at.robthered.simpletodo.features.shared.locale.presentation.model.AppLocaleUi

fun AppLocaleUi.toUiText(): UiText {
    return when(this) {
        AppLocaleUi.DeDE -> UiText.StringResourceId(id = R.string.app_locale_de_DE)
        AppLocaleUi.UsEN -> UiText.StringResourceId(id = R.string.app_locale_us_EN)
    }
}

fun AppLocaleUi.getResId(): Int {
    return when (this) {
        AppLocaleUi.DeDE -> R.drawable.flag_germany
        AppLocaleUi.UsEN -> R.drawable.flag_united_kingdom
    }
}