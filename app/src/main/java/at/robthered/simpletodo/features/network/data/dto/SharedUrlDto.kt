package at.robthered.simpletodo.features.network.data.dto

data class SharedUrlDto(
    val title: String,
    val description: String,
    val link: String,
    val imageUrl: String?,
)