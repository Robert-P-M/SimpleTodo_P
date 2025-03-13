package at.robthered.simpletodo.features.core.domain.state_manager.archived

import at.robthered.simpletodo.features.dataSource.domain.local.model.ArchivedModel

sealed interface ArchivedStateAction {
    data class OnToggleArchived(val archivedModel: ArchivedModel) : ArchivedStateAction
}