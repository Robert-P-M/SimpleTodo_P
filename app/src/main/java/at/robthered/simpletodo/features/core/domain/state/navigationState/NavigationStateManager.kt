package at.robthered.simpletodo.features.core.domain.state.navigationState

interface NavigationStateManager<T> {
    fun handleAction(action: NavigationStateAction<T>)
}