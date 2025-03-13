package at.robthered.simpletodo.features.dataSource.domain.local.relation

import at.robthered.simpletodo.features.dataSource.domain.local.model.ArchivedModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.CompletedModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.EventModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.LinkModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.TaskModel

data class TaskWithDetailsModel(
    val task: TaskModel = TaskModel(),
    val completed: List<CompletedModel> = emptyList(),
    val archived: List<ArchivedModel> = emptyList(),
    val priority: List<PriorityModel> = emptyList(),
    val event: EventModel? = null,
    val links: List<LinkModel> = emptyList()
)