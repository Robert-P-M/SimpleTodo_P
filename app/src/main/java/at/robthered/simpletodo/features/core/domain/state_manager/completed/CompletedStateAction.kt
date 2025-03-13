package at.robthered.simpletodo.features.core.domain.state_manager.completed

import at.robthered.simpletodo.features.dataSource.domain.local.model.CompletedModel

sealed interface CompletedStateAction {
    data class OnToggleCompletion(val completedModel: CompletedModel) : CompletedStateAction
}