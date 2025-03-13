package at.robthered.simpletodo.features.shared.date_time.domain.model

data class AppTime(
    val hour: Int,
    val minute: Int,
    val second: Int = 0,
)