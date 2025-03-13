package at.robthered.simpletodo.features.core.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import at.robthered.simpletodo.features.core.presentation.state.scaffoldState.AppScaffoldStateManagerImpl
import at.robthered.simpletodo.features.permissions.presentation.composables.RequestPostNotificationPermission


@Composable
fun AppScaffold(
    scaffoldState: AppScaffoldStateManagerImpl,
    content: @Composable (paddingValues: PaddingValues, snackbarHostState: SnackbarHostState) -> Unit,
) {
    val state by scaffoldState.state.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }


        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { state.topAppBar?.invoke() },
            floatingActionButton = {
                state.floatingActionButton?.invoke()
            },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            content = { paddingValues ->
                /*RequestPostNotificationPermission(
                    snackbarHostState = snackbarHostState,
                )*/
                content(
                    paddingValues,
                    snackbarHostState,
                )
            }
        )


}