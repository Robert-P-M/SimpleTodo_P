package at.robthered.simpletodo.features.core.domain.state_manager.sharedUrls

import at.robthered.simpletodo.features.network.domain.model.SharedUrlModel
import kotlinx.coroutines.flow.StateFlow

interface SharedUrlsStateManager {
    val sharedUrlModels: StateFlow<List<SharedUrlModel>>
    fun handleAction(action: SharedUrlsStateManagerAction)
}