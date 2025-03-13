package at.robthered.simpletodo.features.homeScreen.presentation

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import at.robthered.simpletodo.features.core.domain.state.navigationState.NavigationStateAction
import at.robthered.simpletodo.features.core.domain.state.scaffoldState.MainScaffoldStateManager
import at.robthered.simpletodo.features.core.presentation.navigation.MainRoute
import at.robthered.simpletodo.features.homeScreen.presentation.components.HomeFloatingActionButtonMenu
import at.robthered.simpletodo.features.homeScreen.presentation.components.HomeTopAppBar
import at.robthered.simpletodo.features.homeScreen.presentation.components.homeItems
import at.robthered.simpletodo.features.homeScreen.presentation.components.remember.rememberHomeMenuState
import at.robthered.simpletodo.features.homeScreen.presentation.state.HomeStateHandler
import at.robthered.simpletodo.features.shared.locale.domain.model.AppLocale
import at.robthered.simpletodo.features.shared.locale.domain.state.AppLocaleStateAction
import at.robthered.simpletodo.features.shared.locale.presentation.components.LocalePickerDialog
import at.robthered.simpletodo.features.shared.locale.presentation.components.state.rememberLocalePickerState
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreenRoot(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = koinViewModel<HomeViewModel>(),
    navigateTo: (route: MainRoute) -> Unit,
    appScaffoldStateManager: MainScaffoldStateManager,
) {

    val stateHandler = remember {
        HomeStateHandler(
            homeViewModel = homeViewModel,
            navigateTo = navigateTo,
        )
    }

    HomeScreen(
        modifier = modifier,
        stateHandler = stateHandler,
        appScaffoldStateManager = appScaffoldStateManager,
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    stateHandler: HomeStateHandler,
    appScaffoldStateManager: MainScaffoldStateManager,
) {

    val appLocaleState by stateHandler.appLocaleState.collectAsStateWithLifecycle()
    val homeItemsResource by stateHandler.homeItemsResource.collectAsStateWithLifecycle()
    val expandedTasksState by stateHandler.expandedTasksState.collectAsStateWithLifecycle()
    val expandedSectionsState by stateHandler.expandedSectionsState.collectAsStateWithLifecycle()

    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(state = topAppBarState)
    val lazyListState = rememberLazyListState()


    val localePickerState = rememberLocalePickerState(
        currentLocale = appLocaleState.appLocale,
        onSave = { appLocale: AppLocale ->
            stateHandler.onAppLocaleStateAction(
                AppLocaleStateAction.OnPickAppLocale(appLocale = appLocale)
            )
        },
    )



    val homeMenuState = rememberHomeMenuState(
        onOpenLocalePicker = localePickerState::onShow,
        onNavigateToAddSectionDialog = stateHandler.navigateTo
    )

    HomeTopAppBar(
        modifier = Modifier,
        homeMenuState = homeMenuState,
        appScaffoldStateManager = appScaffoldStateManager,
        scrollBehavior = scrollBehavior,
    )
    HomeFloatingActionButtonMenu(
        modifier = Modifier,
        appScaffoldStateManager = appScaffoldStateManager,
        onNavigateToAddTaskDialog = stateHandler.navigateTo,
        onNavigateToAddSectionDialog = stateHandler.navigateTo,
    )

    LocalePickerDialog(
        localePickerState = localePickerState
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        state = lazyListState,
    ) {
        homeItems(
            homeItemsResource = homeItemsResource,
            expandedTasks = expandedTasksState,
            expandedSections = expandedSectionsState,
            stateHandler = stateHandler
        )
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }

    }

}