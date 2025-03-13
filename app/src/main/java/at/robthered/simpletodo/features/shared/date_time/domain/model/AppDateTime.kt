package at.robthered.simpletodo.features.shared.date_time.domain.model

import androidx.room.Embedded
import at.robthered.simpletodo.features.core.utils.ext.toInstant
import at.robthered.simpletodo.features.shared.date_time.data.mapper.toLocalDate
import at.robthered.simpletodo.features.shared.date_time.data.mapper.toLocalTime

data class AppDateTime(
    /*@Embedded(prefix = "date_") */val date: AppDate,
    /*@Embedded(prefix = "time_") */val time: AppTime,
  /*  val epochMillis: Long*/
) /*{
    companion object {
        fun from(date: AppDate, time: AppTime): AppDateTime {
            val epochMillis = date.toLocalDate().toInstant().toEpochMilliseconds() + time.toLocalTime().toMillisecondOfDay()
            return AppDateTime(date, time, epochMillis)
        }
    }
}*/