package at.robthered.simpletodo.features.core.data.state_manager.completed

import at.robthered.simpletodo.features.dataSource.domain.use_case.completed.CompleteTaskUseCase
import at.robthered.simpletodo.features.core.domain.state_manager.completed.CompletedStateAction
import at.robthered.simpletodo.features.core.domain.state_manager.completed.CompletedStateManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CompletedStateManagerImpl(
    private val coroutineScope: CoroutineScope,
    private val completeTaskUseCase: CompleteTaskUseCase,
) : CompletedStateManager {

    private fun onToggleCompletion(
        action: CompletedStateAction.OnToggleCompletion
    ) = coroutineScope.launch {
        completeTaskUseCase(completedModel = action.completedModel)
    }

    override fun handleAction(action: CompletedStateAction) {
        when (action) {
            is CompletedStateAction.OnToggleCompletion -> onToggleCompletion(action)
        }
    }

}