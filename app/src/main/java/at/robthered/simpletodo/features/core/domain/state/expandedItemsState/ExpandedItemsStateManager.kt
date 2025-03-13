package at.robthered.simpletodo.features.core.domain.state.expandedItemsState

import kotlinx.coroutines.flow.StateFlow

interface ExpandedItemsStateManager {
    val state: StateFlow<ExpandedItemsState>
    fun handleAction(action: ExpandedItemsStateAction)
}