package at.robthered.simpletodo.features.core.domain.state.scaffoldState

import at.robthered.simpletodo.features.core.utils.FloatingActionButtonComposable
import at.robthered.simpletodo.features.core.utils.TopAppBarComposable

data class MainScaffoldState(
    val topAppBar: TopAppBarComposable? = null,
    val floatingActionButton: FloatingActionButtonComposable? = null,
)