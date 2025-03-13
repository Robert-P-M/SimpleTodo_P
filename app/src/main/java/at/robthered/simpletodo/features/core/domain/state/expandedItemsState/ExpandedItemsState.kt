package at.robthered.simpletodo.features.core.domain.state.expandedItemsState

data class ExpandedItemsState(
    val expandedItems: Set<Long> = emptySet<Long>(),
)