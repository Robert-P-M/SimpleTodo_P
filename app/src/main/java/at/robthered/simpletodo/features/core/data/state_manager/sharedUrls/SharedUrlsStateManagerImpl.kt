package at.robthered.simpletodo.features.core.data.state_manager.sharedUrls

import at.robthered.simpletodo.features.core.domain.eventBus.clear
import at.robthered.simpletodo.features.core.domain.eventBus.events.SharedUrlModelEvent
import at.robthered.simpletodo.features.core.domain.eventBus.events.SharedUrlModelEventBus
import at.robthered.simpletodo.features.core.domain.eventBus.on
import at.robthered.simpletodo.features.core.domain.state_manager.sharedUrls.SharedUrlsStateManager
import at.robthered.simpletodo.features.core.domain.state_manager.sharedUrls.SharedUrlsStateManagerAction
import at.robthered.simpletodo.features.network.domain.model.SharedUrlModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SharedUrlsStateManagerImpl(
    coroutineScope: CoroutineScope,
    private val shareUrlModelEventBus: SharedUrlModelEventBus,
): SharedUrlsStateManager {

    private val _sharedUrlModels: MutableStateFlow<List<SharedUrlModel>> =
        MutableStateFlow(emptyList())
    override val sharedUrlModels: StateFlow<List<SharedUrlModel>>
        get() = _sharedUrlModels.asStateFlow()

    override fun handleAction(action: SharedUrlsStateManagerAction) {
        when (action) {
            is SharedUrlsStateManagerAction.OnAddSharedUrlModel -> onAddSharedUrlModel(action)
            is SharedUrlsStateManagerAction.OnRemoveSharedUrlModel -> onRemoveSharedUrlModel(action)
            SharedUrlsStateManagerAction.OnClearState -> onClearState()
            is SharedUrlsStateManagerAction.OnInitializeState -> onInitializeState(action)
        }
    }
    private val shareUrlModelEvents = shareUrlModelEventBus
        .on<SharedUrlModelEvent, SharedUrlModelEvent.NewSharedUrlModelEvent>(scope = coroutineScope) { event ->
            handleAction(
                SharedUrlsStateManagerAction
                    .OnAddSharedUrlModel(
                        sharedUrlModel = event.sharedUrlModel
                    )
            )
            shareUrlModelEventBus.clear(SharedUrlModelEvent.ClearEvent)
        }

    private fun onInitializeState(action: SharedUrlsStateManagerAction.OnInitializeState) {
        _sharedUrlModels.value = action.sharedUrlModels
    }

    private fun onClearState() {
        _sharedUrlModels.value = emptyList()
    }

    private fun onRemoveSharedUrlModel(action: SharedUrlsStateManagerAction.OnRemoveSharedUrlModel) {
        _sharedUrlModels.update { currentSharedUrlModels ->
            currentSharedUrlModels.filterNot { it.link == action.link }
        }
    }

    private fun onAddSharedUrlModel(action: SharedUrlsStateManagerAction.OnAddSharedUrlModel) {
        _sharedUrlModels.update {
            it + action.sharedUrlModel
        }
    }
}