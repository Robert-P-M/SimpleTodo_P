package at.robthered.simpletodo.features.dataSource.data.ext

import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityModel
import kotlinx.datetime.Clock

fun PriorityModel.clearStatus(): PriorityModel {
    return this.copy(
        priorityId = null,
        createdAt = Clock.System.now()
    )
}