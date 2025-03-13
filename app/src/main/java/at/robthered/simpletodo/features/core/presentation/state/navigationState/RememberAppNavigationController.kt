package at.robthered.simpletodo.features.core.presentation.state.navigationState

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import at.robthered.simpletodo.features.core.domain.state.navigationState.NavigationStateManager
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.inject

@Composable
fun rememberAppNavigationStateManager(
    navController: NavHostController = rememberNavController(),
): MainNavigationStateManagerImpl {

    val lifecycleOwner = LocalLifecycleOwner.current
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }

    // ViewModelStore SOFORT nach der Erstellung setzen!
    LaunchedEffect(navController, viewModelStoreOwner) {
        navController.setViewModelStore(viewModelStoreOwner.viewModelStore)
    }

    DisposableEffect(lifecycleOwner, viewModelStoreOwner, navController) {
        navController.setLifecycleOwner(lifecycleOwner)
        // ViewModelStore wird *nicht* mehr hier gesetzt!
        onDispose {}
    }
    val appNavigationStateManager: MainNavigationStateManagerImpl by remember {
        inject(
            parameters = { parametersOf(navController) },
            clazz = NavigationStateManager::class.java
        )
    }
    return appNavigationStateManager
}