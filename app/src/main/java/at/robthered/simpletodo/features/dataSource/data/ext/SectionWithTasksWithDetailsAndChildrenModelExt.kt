package at.robthered.simpletodo.features.dataSource.data.ext

import at.robthered.simpletodo.features.dataSource.domain.local.relation.SectionWithTasksWithDetailsAndChildrenModel
import at.robthered.simpletodo.features.homeScreen.domain.model.ScreenItemsModel

fun SectionWithTasksWithDetailsAndChildrenModel.toScreenItemModel(): ScreenItemsModel {
    return ScreenItemsModel(
        section = section,
        tasks = tasks
    )
}

fun List<SectionWithTasksWithDetailsAndChildrenModel>.toScreenItemModels(): List<ScreenItemsModel> {
    return this.map { it.toScreenItemModel()}
}