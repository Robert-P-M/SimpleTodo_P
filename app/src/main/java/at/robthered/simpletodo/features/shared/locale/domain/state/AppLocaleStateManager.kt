package at.robthered.simpletodo.features.shared.locale.domain.state

import kotlinx.coroutines.flow.StateFlow

interface AppLocaleStateManager {
    val state: StateFlow<AppLocaleState>
    fun handleAction(action: AppLocaleStateAction)
}