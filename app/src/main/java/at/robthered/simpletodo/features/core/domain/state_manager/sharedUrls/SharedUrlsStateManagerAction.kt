package at.robthered.simpletodo.features.core.domain.state_manager.sharedUrls

import at.robthered.simpletodo.features.network.domain.model.SharedUrlModel

sealed interface SharedUrlsStateManagerAction {
    data class OnAddSharedUrlModel(val sharedUrlModel: SharedUrlModel): SharedUrlsStateManagerAction
    data class OnRemoveSharedUrlModel(val link: String): SharedUrlsStateManagerAction
    data object OnClearState: SharedUrlsStateManagerAction
    data class OnInitializeState(val sharedUrlModels: List<SharedUrlModel>):
        SharedUrlsStateManagerAction
}