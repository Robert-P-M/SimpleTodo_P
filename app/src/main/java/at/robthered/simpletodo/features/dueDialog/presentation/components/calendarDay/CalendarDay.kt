package at.robthered.simpletodo.features.dueDialog.presentation.components.calendarDay

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import at.robthered.simpletodo.features.core.presentation.ext.modifier.pickedItemCircle
import at.robthered.simpletodo.features.core.presentation.ext.modifier.todayItemCircle

@Composable
fun RowScope.CalendarDay(
    calendarDayUiModel: CalendarDayUiModel,
) {

    Text(
        modifier = Modifier
            .weight(1f)
            .then(
                if (calendarDayUiModel.isCurrentDay) {
                    Modifier.todayItemCircle()
                } else Modifier
            )
            .then(
                if (calendarDayUiModel.isPickedDay) {
                    Modifier.pickedItemCircle()
                } else Modifier
            )
            .clickable(
                enabled = calendarDayUiModel.isEnabled
            ) {
                calendarDayUiModel.onClick()
            }
            .padding(4.dp)
            .alpha(
                alpha = calendarDayUiModel.itemAlpha
            ),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyMedium,
        text = calendarDayUiModel.text,
        fontWeight = FontWeight.Medium
    )
}