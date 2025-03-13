package at.robthered.simpletodo.features.dataSource.data.ext

import at.robthered.simpletodo.features.dataSource.domain.local.relation.TaskWithDetailsAndChildrenModel
import at.robthered.simpletodo.features.homeScreen.domain.model.ScreenItemsModel

fun List<TaskWithDetailsAndChildrenModel>.toScreenItemModel() : ScreenItemsModel {
    return ScreenItemsModel(
        tasks = this
    )
}