package at.robthered.simpletodo.features.core.presentation.state

import at.robthered.simpletodo.features.core.domain.state.expandedItemsState.ExpandedItemsState
import at.robthered.simpletodo.features.core.domain.state.expandedItemsState.ExpandedItemsStateAction
import at.robthered.simpletodo.features.core.domain.state.expandedItemsState.ExpandedItemsStateManager
import at.robthered.simpletodo.features.core.utils.ext.toggle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class ExpandedItemsStateManagerImpl(
    private val coroutineScope: CoroutineScope,
) : ExpandedItemsStateManager {
    private val _state = MutableStateFlow(ExpandedItemsState())
    override val state: StateFlow<ExpandedItemsState>
        get() = _state
            .stateIn(
                scope = coroutineScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = ExpandedItemsState()
            )


    private fun onToggleTaskExpanded(action: ExpandedItemsStateAction.OnToggleItemExpanded) {
        action.itemId?.let {
            _state.update {
                it.copy(
                    expandedItems = it.expandedItems.toggle(action.itemId)
                )
            }
        }
    }

    override fun handleAction(action: ExpandedItemsStateAction) {
        when (action) {
            is ExpandedItemsStateAction.OnToggleItemExpanded ->
                onToggleTaskExpanded(action)
        }
    }
}