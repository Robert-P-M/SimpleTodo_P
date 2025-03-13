package at.robthered.simpletodo.features.dueDialog.presentation.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppCalendarDayItem

@Composable
fun RowScope.CalendarWeek(
    appCalendarDayItem: AppCalendarDayItem.AppCalendarWeek,
) {
    Text(
        modifier = Modifier
            .weight(1f)
            .alpha(alpha = 0.7f),
        textAlign = TextAlign.Center,
        text = appCalendarDayItem.isoWeekNumber.toString()
            .padStart(2, '0')
    )
}