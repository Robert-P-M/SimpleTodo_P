package at.robthered.simpletodo.features.network.data.mapper

import at.robthered.simpletodo.features.network.data.dto.SharedUrlDto
import at.robthered.simpletodo.features.network.domain.model.SharedUrlModel

fun SharedUrlDto.toModel(): SharedUrlModel {
    return SharedUrlModel(
        title = title,
        description = description,
        link = link,
        imageUrl = imageUrl
    )
}