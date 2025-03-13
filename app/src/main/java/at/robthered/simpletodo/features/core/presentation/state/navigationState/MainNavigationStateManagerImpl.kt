package at.robthered.simpletodo.features.core.presentation.state.navigationState

import androidx.navigation.NavHostController
import at.robthered.simpletodo.features.core.domain.state.navigationState.NavigationStateAction
import at.robthered.simpletodo.features.core.domain.state.navigationState.NavigationStateManager
import at.robthered.simpletodo.features.core.presentation.navigation.MainRoute

class MainNavigationStateManagerImpl(
    val navController: NavHostController,
) : NavigationStateManager<MainRoute> {
    override fun handleAction(action: NavigationStateAction<MainRoute>) {
        when (action) {
            NavigationStateAction.OnNavigateBack -> navController.navigateUp()
            is NavigationStateAction.Navigate -> navController.navigate(action.route)
        }
    }
}