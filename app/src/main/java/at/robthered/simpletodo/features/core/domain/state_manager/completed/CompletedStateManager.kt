package at.robthered.simpletodo.features.core.domain.state_manager.completed

interface CompletedStateManager {
    fun handleAction(action: CompletedStateAction)
}