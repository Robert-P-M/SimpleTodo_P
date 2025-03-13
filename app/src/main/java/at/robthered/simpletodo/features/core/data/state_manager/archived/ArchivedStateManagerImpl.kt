package at.robthered.simpletodo.features.core.data.state_manager.archived

import at.robthered.simpletodo.features.dataSource.domain.use_case.archived.ArchiveTaskUseCase
import at.robthered.simpletodo.features.core.domain.state_manager.archived.ArchivedStateAction
import at.robthered.simpletodo.features.core.domain.state_manager.archived.ArchivedStateManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ArchivedStateManagerImpl(
    private val coroutineScope: CoroutineScope,
    private val archiveTaskUseCase: ArchiveTaskUseCase
) : ArchivedStateManager {

    private fun onToggleArchived(action: ArchivedStateAction.OnToggleArchived)
    = coroutineScope.launch{
        archiveTaskUseCase(archivedModel = action.archivedModel)
    }

    override fun handleAction(action: ArchivedStateAction) {
        when (action) {
            is ArchivedStateAction.OnToggleArchived -> onToggleArchived(action)
        }
    }
}