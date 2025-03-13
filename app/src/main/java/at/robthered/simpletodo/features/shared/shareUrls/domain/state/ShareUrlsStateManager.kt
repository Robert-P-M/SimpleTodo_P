package at.robthered.simpletodo.features.shared.shareUrls.domain.state

import kotlinx.coroutines.flow.StateFlow

interface ShareUrlsStateManager {
    val state: StateFlow<SharedUrlsState>
    fun handleAction(action: ShareUrlsStateAction)
}