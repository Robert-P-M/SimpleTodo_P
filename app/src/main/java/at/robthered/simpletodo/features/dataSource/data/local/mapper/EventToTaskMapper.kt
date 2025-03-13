package at.robthered.simpletodo.features.dataSource.data.local.mapper

import at.robthered.simpletodo.features.dataSource.data.local.relation.EventToTaskRelation
import at.robthered.simpletodo.features.dataSource.domain.local.relation.EventToTaskModel

fun EventToTaskRelation.toModel(): EventToTaskModel {
    return EventToTaskModel(
        task = task.toTaskModel(),
        currentEvent = currentEvent.toEventModel()
    )
}

fun EventToTaskModel.toEntity(): EventToTaskRelation {
    return EventToTaskRelation(
        task = task.toTaskEntity(),
        currentEvent = currentEvent.toEventEntity()
    )
}