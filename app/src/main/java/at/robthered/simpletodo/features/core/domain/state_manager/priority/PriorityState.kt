package at.robthered.simpletodo.features.core.domain.state_manager.priority

import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityModel

data class PriorityState(
    val isDialogVisible: Boolean = false,
    val currentPriority: PriorityModel? = null,
)