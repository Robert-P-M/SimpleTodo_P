package at.robthered.simpletodo.features.homeScreen.presentation.state

import at.robthered.simpletodo.features.core.domain.state.expandedItemsState.ExpandedItemsState
import at.robthered.simpletodo.features.core.domain.state.expandedItemsState.ExpandedItemsStateAction
import at.robthered.simpletodo.features.core.presentation.navigation.MainRoute
import at.robthered.simpletodo.features.core.domain.state_manager.archived.ArchivedStateAction
import at.robthered.simpletodo.features.core.domain.state_manager.completed.CompletedStateAction
import at.robthered.simpletodo.features.homeScreen.presentation.HomeScreenAction
import at.robthered.simpletodo.features.homeScreen.presentation.HomeViewModel
import at.robthered.simpletodo.features.shared.locale.domain.state.AppLocaleStateAction
import kotlinx.coroutines.flow.StateFlow

class HomeStateHandler(
    private val homeViewModel: HomeViewModel,
    val navigateTo: (route: MainRoute) -> Unit,
) {

    fun handleHomeStateManagerAction(action: HomeScreenAction) = homeViewModel.handleAction(action)

    val expandedTasksState: StateFlow<ExpandedItemsState> = homeViewModel.expandedTasksState
    fun onExpandedTasksStateAction(action: ExpandedItemsStateAction) {
        homeViewModel.onExpandedTasksStateAction(action)
    }

    val expandedSectionsState: StateFlow<ExpandedItemsState> = homeViewModel.expandedSectionsState
    fun onExpandedSectionsStateAction(action: ExpandedItemsStateAction) {
        homeViewModel.onExpandedSectionsStateAction(action)
    }

    val homeItemsResource = homeViewModel.homeItemsResource

    fun onCompletedStateAction(action: CompletedStateAction) {
        homeViewModel.onCompletedStateAction(action)
    }

    fun onArchivedStateAction(action: ArchivedStateAction) {
        homeViewModel.onArchivedStateAction(action)
    }

    val appLocaleState = homeViewModel.appLocaleState

    fun onAppLocaleStateAction(action: AppLocaleStateAction) {
        homeViewModel.onAppLocaleStateAction(action)
    }


}