package at.robthered.simpletodo.features.dataSource.data.local.mapper

import at.robthered.simpletodo.features.dataSource.data.local.relation.TaskWithDetailsRelation
import at.robthered.simpletodo.features.dataSource.domain.local.relation.TaskWithDetailsModel

fun TaskWithDetailsRelation.toModel(): TaskWithDetailsModel {
    return TaskWithDetailsModel(
        task = task.toTaskModel(),
        completed = completed.map { entity -> entity.toCompletedModel() },
        archived = archived.map { entity -> entity.toArchivedModel() },
        priority = priority.map { entity -> entity.toPriorityModel() },
        event = event?.toEventModel()
    )
}