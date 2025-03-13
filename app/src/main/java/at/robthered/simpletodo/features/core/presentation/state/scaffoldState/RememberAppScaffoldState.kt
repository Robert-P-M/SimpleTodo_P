package at.robthered.simpletodo.features.core.presentation.state.scaffoldState

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope

@Composable
fun rememberAppScaffoldStateManager(): AppScaffoldStateManagerImpl {

    val scope = rememberCoroutineScope()
    return AppScaffoldStateManagerImpl(scope)
}