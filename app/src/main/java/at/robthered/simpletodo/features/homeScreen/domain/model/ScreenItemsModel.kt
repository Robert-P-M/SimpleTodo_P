package at.robthered.simpletodo.features.homeScreen.domain.model

import at.robthered.simpletodo.features.dataSource.domain.local.model.SectionModel
import at.robthered.simpletodo.features.dataSource.domain.local.relation.TaskWithDetailsAndChildrenModel

data class ScreenItemsModel(
    val section: SectionModel? = null,
    val tasks: List<TaskWithDetailsAndChildrenModel> = emptyList()
)