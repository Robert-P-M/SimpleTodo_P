package at.robthered.simpletodo.features.dataSource.data.ext

import at.robthered.simpletodo.features.dataSource.domain.local.model.EventModel
import kotlinx.datetime.Clock

fun EventModel.clearStatus(): EventModel {
    return this.copy(
        eventId = null,
        createdAt = Clock.System.now()
    )
}