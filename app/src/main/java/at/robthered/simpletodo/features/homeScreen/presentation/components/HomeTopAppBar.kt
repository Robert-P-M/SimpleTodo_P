package at.robthered.simpletodo.features.homeScreen.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.domain.state.scaffoldState.MainScaffoldStateAction
import at.robthered.simpletodo.features.core.domain.state.scaffoldState.MainScaffoldStateManager
import at.robthered.simpletodo.features.core.presentation.components.AppDropdownMenu
import at.robthered.simpletodo.features.core.presentation.components.AppDropdownMenuItem
import at.robthered.simpletodo.features.core.utils.TopAppBarComposable
import at.robthered.simpletodo.features.homeScreen.presentation.components.remember.HomeMenuState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    appScaffoldStateManager: MainScaffoldStateManager,
    homeMenuState: HomeMenuState,
) {
    val homeTopAppBarDropdownMenuItems = remember {
        homeMenuState.menuItems
    }

    val topAppBar: TopAppBarComposable = {
        TopAppBar(
            modifier = modifier,
            scrollBehavior = scrollBehavior,
            title = {
                Text(
                    text = stringResource(R.string.top_app_bar_title_home_screen)
                )
            },
            actions = {
                IconButton(
                    onClick = homeMenuState::onShow
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Open Dropdown menu"
                    )
                }
                AppDropdownMenu(
                    expanded = homeMenuState.isVisible,
                    onDismissRequest = homeMenuState::onHide,
                    offset = DpOffset(
                        x = 0.dp,
                        y = 0.dp
                    )
                ) {
                    homeTopAppBarDropdownMenuItems.map { menuItem ->
                        AppDropdownMenuItem(
                            menuItem = menuItem
                        )
                    }
                }
            }
        )
    }


    LaunchedEffect(
        Unit
    ) {
        appScaffoldStateManager.handleAction(
            MainScaffoldStateAction.OnSetTopAppBar(topAppBar)
        )
    }


}