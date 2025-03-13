package at.robthered.simpletodo.features.shared.locale.presentation.state

import at.robthered.simpletodo.features.shared.locale.domain.AppLocaleProvider
import at.robthered.simpletodo.features.shared.locale.domain.state.AppLocaleState
import at.robthered.simpletodo.features.shared.locale.domain.state.AppLocaleStateAction
import at.robthered.simpletodo.features.shared.locale.domain.state.AppLocaleStateManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppLocaleStateManagerImpl(
    private val coroutineScope: CoroutineScope,
    private val localeProvider: AppLocaleProvider,
): AppLocaleStateManager {

    private var loadLocaleJob: Job? = null
    private val _state: MutableStateFlow<AppLocaleState> = MutableStateFlow(AppLocaleState())
    override val state: StateFlow<AppLocaleState>
        get() = _state
            .onStart {
                loadLocale()
            }
            .stateIn(
                scope = coroutineScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = AppLocaleState()
            )


    private fun loadLocale()  {
        loadLocaleJob?.cancel()
        loadLocaleJob = coroutineScope.launch {
            val appLocale = localeProvider.getLocale()
            _state.update {
                it.copy(
                    appLocale = appLocale
                )
            }
        }
    }

    override fun handleAction(action: AppLocaleStateAction) {
        when (action) {
            is AppLocaleStateAction.OnPickAppLocale -> onPickLocale(action)
        }
    }

    private fun onPickLocale(action: AppLocaleStateAction.OnPickAppLocale) {
        localeProvider.setLocale(action.appLocale)
        _state.update {
            it.copy(
                appLocale = action.appLocale,
            )
        }
    }

}