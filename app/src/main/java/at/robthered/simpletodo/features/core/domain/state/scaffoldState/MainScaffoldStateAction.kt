package at.robthered.simpletodo.features.core.domain.state.scaffoldState

import at.robthered.simpletodo.features.core.utils.FloatingActionButtonComposable
import at.robthered.simpletodo.features.core.utils.TopAppBarComposable

sealed interface MainScaffoldStateAction {
    data class OnSetTopAppBar(val topAppBar: TopAppBarComposable?) : MainScaffoldStateAction
    data class OnSetFloatingActionButton(val floatingActionButton: FloatingActionButtonComposable) :
        MainScaffoldStateAction

    data object OnClearScaffoldElements : MainScaffoldStateAction
}