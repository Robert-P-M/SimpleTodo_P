package at.robthered.simpletodo.features.shared.date_time.presentation.ext

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.NextWeek
import androidx.compose.material.icons.outlined.Today
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material.icons.outlined.Weekend
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.presentation.UiText
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppUpcomingDate
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppUpcomingDateEnum

@Composable
fun AppUpcomingDate.getTitleShort():String {
    return when (this.appUpcomingDateEnum) {
        AppUpcomingDateEnum.TODAY -> appDate.toDayOfWeek(2)
        AppUpcomingDateEnum.TOMORROW -> appDate.toDayOfWeek(2)
        AppUpcomingDateEnum.THIS_WEEKEND -> appDate.toDayOfWeekWithDate()
        AppUpcomingDateEnum.NEXT_WEEK -> appDate.toDayOfWeekWithDate()
        AppUpcomingDateEnum.NEXT_WEEKEND -> appDate.toDayOfWeekWithDate()
    }
}

fun AppUpcomingDate.toUiText(): UiText {
    return when (appUpcomingDateEnum) {
        AppUpcomingDateEnum.TODAY -> UiText.StringResourceId(id = R.string.upcoming_day_today)
        AppUpcomingDateEnum.TOMORROW -> UiText.StringResourceId(id = R.string.upcoming_day_tomorrow)
        AppUpcomingDateEnum.THIS_WEEKEND -> UiText.StringResourceId(id = R.string.upcoming_day_this_weekend)
        AppUpcomingDateEnum.NEXT_WEEK -> UiText.StringResourceId(id = R.string.upcoming_day_next_week)
        AppUpcomingDateEnum.NEXT_WEEKEND -> UiText.StringResourceId(id = R.string.upcoming_day_next_weekend)
    }
}

fun AppUpcomingDate.getImageVector() : ImageVector {
    return when (appUpcomingDateEnum) {
        AppUpcomingDateEnum.TODAY -> Icons.Outlined.Today
        AppUpcomingDateEnum.TOMORROW -> Icons.Outlined.WbSunny
        AppUpcomingDateEnum.THIS_WEEKEND,
        AppUpcomingDateEnum.NEXT_WEEKEND
            -> Icons.Outlined.Weekend
        AppUpcomingDateEnum.NEXT_WEEK -> Icons.AutoMirrored.Outlined.NextWeek
    }
}