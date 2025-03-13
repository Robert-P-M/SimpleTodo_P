package at.robthered.simpletodo.features.core.domain.state.expandedItemsState

sealed interface ExpandedItemsStateAction {
    data class OnToggleItemExpanded(val itemId: Long?) : ExpandedItemsStateAction
}