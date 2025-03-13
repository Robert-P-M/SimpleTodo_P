package at.robthered.simpletodo.features.shared.shareUrls.domain.state

import at.robthered.simpletodo.features.network.domain.model.SharedUrlModel

sealed interface ShareUrlsStateAction {
    data class OnAddSharedUrl(val sharedUrlModel: SharedUrlModel): ShareUrlsStateAction
    data class OnRemoveSharedUrl(val link: String): ShareUrlsStateAction
    data object ClearAll: ShareUrlsStateAction
}