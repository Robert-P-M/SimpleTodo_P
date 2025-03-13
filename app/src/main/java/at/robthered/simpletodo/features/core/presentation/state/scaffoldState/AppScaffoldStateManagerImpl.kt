package at.robthered.simpletodo.features.core.presentation.state.scaffoldState

import at.robthered.simpletodo.features.core.domain.state.scaffoldState.MainScaffoldState
import at.robthered.simpletodo.features.core.domain.state.scaffoldState.MainScaffoldStateAction
import at.robthered.simpletodo.features.core.domain.state.scaffoldState.MainScaffoldStateManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class AppScaffoldStateManagerImpl(
    private val coroutineScope: CoroutineScope,
) : MainScaffoldStateManager {


    private val _state = MutableStateFlow(MainScaffoldState())
    override val state: StateFlow<MainScaffoldState>
        get() = _state
            .stateIn(
                scope = coroutineScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = MainScaffoldState()
            )

    override fun handleAction(action: MainScaffoldStateAction) {
        when (action) {
            MainScaffoldStateAction.OnClearScaffoldElements -> clearScaffoldElements()
            is MainScaffoldStateAction.OnSetFloatingActionButton -> setFloatingActionButton(action)
            is MainScaffoldStateAction.OnSetTopAppBar -> setTopAppBar(action)
        }
    }

    private fun setTopAppBar(action: MainScaffoldStateAction.OnSetTopAppBar) {
        _state.update {
            it.copy(
                topAppBar = action.topAppBar
            )
        }
    }

    private fun setFloatingActionButton(action: MainScaffoldStateAction.OnSetFloatingActionButton) {
        _state.update {
            it.copy(
                floatingActionButton = action.floatingActionButton
            )
        }
    }

    private fun clearScaffoldElements() {
        _state.update {
            MainScaffoldState()
        }
    }
}