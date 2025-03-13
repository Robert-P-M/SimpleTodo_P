package at.robthered.simpletodo.features.core.domain.state.scaffoldState

import kotlinx.coroutines.flow.StateFlow

interface MainScaffoldStateManager {
    val state: StateFlow<MainScaffoldState>
    fun handleAction(action: MainScaffoldStateAction)
}