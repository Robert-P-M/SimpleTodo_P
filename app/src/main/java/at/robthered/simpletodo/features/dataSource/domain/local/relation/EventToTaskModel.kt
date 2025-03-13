package at.robthered.simpletodo.features.dataSource.domain.local.relation

import at.robthered.simpletodo.features.dataSource.domain.local.model.EventModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.TaskModel

data class EventToTaskModel(
    val task: TaskModel,
    val currentEvent: EventModel
)