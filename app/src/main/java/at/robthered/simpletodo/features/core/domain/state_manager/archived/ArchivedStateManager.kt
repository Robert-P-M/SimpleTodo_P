package at.robthered.simpletodo.features.core.domain.state_manager.archived

interface ArchivedStateManager {
    fun handleAction(action: ArchivedStateAction)
}