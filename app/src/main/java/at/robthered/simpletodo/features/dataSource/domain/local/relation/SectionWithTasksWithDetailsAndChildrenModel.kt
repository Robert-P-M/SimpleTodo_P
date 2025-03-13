package at.robthered.simpletodo.features.dataSource.domain.local.relation

import at.robthered.simpletodo.features.dataSource.domain.local.model.SectionModel

data class SectionWithTasksWithDetailsAndChildrenModel(
    val section: SectionModel,
    val tasks: List<TaskWithDetailsAndChildrenModel> = emptyList()
)