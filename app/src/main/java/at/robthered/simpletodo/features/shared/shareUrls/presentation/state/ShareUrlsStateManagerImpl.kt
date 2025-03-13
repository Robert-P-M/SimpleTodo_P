package at.robthered.simpletodo.features.shared.shareUrls.presentation.state

import android.util.Log
import at.robthered.simpletodo.features.shared.shareUrls.domain.state.ShareUrlsStateAction
import at.robthered.simpletodo.features.shared.shareUrls.domain.state.ShareUrlsStateManager
import at.robthered.simpletodo.features.shared.shareUrls.domain.state.SharedUrlsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class ShareUrlsStateManagerImpl(
    private val coroutineScope: CoroutineScope,
): ShareUrlsStateManager {
    private val _state: MutableStateFlow<SharedUrlsState> = MutableStateFlow(SharedUrlsState())
    override val state: StateFlow<SharedUrlsState>
        get() = _state
            .stateIn(
                scope = coroutineScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = SharedUrlsState()
            )


    override fun handleAction(action: ShareUrlsStateAction) {
        when (action) {
            is ShareUrlsStateAction.OnAddSharedUrl -> onAddSharedUrl(action)
            is ShareUrlsStateAction.OnRemoveSharedUrl -> onRemoveSharedUrl(action)
            ShareUrlsStateAction.ClearAll -> clearAll()
        }
    }

    private fun clearAll() {
        _state.update {
            it.copy(
                sharedUrlModels = emptyList()
            )
        }
    }

    private fun onRemoveSharedUrl(action: ShareUrlsStateAction.OnRemoveSharedUrl) {
        _state.update { currentState ->
            currentState.copy(
                sharedUrlModels = currentState.sharedUrlModels.filterNot { it.link == action.link }
            )
        }
    }

    private fun onAddSharedUrl(action: ShareUrlsStateAction.OnAddSharedUrl) {
        _state.update { currentState ->
            currentState.copy(
                sharedUrlModels = currentState.sharedUrlModels + action.sharedUrlModel
            )
        }
    }

}