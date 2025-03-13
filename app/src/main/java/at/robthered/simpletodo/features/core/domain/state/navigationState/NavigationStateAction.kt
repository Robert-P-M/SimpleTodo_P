package at.robthered.simpletodo.features.core.domain.state.navigationState


sealed interface NavigationStateAction<out T> {
    data object OnNavigateBack : NavigationStateAction<Nothing>
    data class Navigate<T>(val route: T): NavigationStateAction<T>
}