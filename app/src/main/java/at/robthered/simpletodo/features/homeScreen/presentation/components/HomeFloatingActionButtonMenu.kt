package at.robthered.simpletodo.features.homeScreen.presentation.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButtonMenu
import androidx.compose.material3.FloatingActionButtonMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleFloatingActionButton
import androidx.compose.material3.ToggleFloatingActionButtonDefaults.animateIcon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.domain.state.scaffoldState.MainScaffoldStateAction
import at.robthered.simpletodo.features.core.domain.state.scaffoldState.MainScaffoldStateManager
import at.robthered.simpletodo.features.core.presentation.UiText
import at.robthered.simpletodo.features.core.presentation.icons.AddSection
import at.robthered.simpletodo.features.core.presentation.navigation.MainRoute
import at.robthered.simpletodo.features.core.presentation.uiModel.FloatingActionButtonMenuItem
import at.robthered.simpletodo.features.core.utils.FloatingActionButtonComposable

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomeFloatingActionButtonMenu(
    modifier: Modifier = Modifier,
    onNavigateToAddTaskDialog: (args: MainRoute.AddTaskDialog) -> Unit,
    onNavigateToAddSectionDialog: (args: MainRoute.AddSectionDialog) -> Unit,
    appScaffoldStateManager: MainScaffoldStateManager,
) {

    var fabMenuExpanded by rememberSaveable { mutableStateOf(false) }

    BackHandler(fabMenuExpanded) { fabMenuExpanded = false }

    val context = LocalContext.current

    val floatingActionButtonMenuItems = listOf(
        FloatingActionButtonMenuItem.Default(
            text = UiText.StringResourceId(id = R.string.floating_action_button_menu_item_text_new_task),
            onClick = { onNavigateToAddTaskDialog.invoke(MainRoute.AddTaskDialog()) },
            icon = Icons.Outlined.Add
        ),
        FloatingActionButtonMenuItem.Default(
            text = UiText.StringResourceId(id = R.string.floating_action_button_menu_item_text_new_section),
            onClick = { onNavigateToAddSectionDialog(MainRoute.AddSectionDialog()) },
            icon = AddSection
        )
    )


    val floatingActionButton: FloatingActionButtonComposable = {
        FloatingActionButtonMenu(
            modifier = modifier,
            expanded = fabMenuExpanded,
            button = {
                ToggleFloatingActionButton(
                    modifier =
                    Modifier.semantics {
                        traversalIndex = -1f
                        stateDescription =
                            if (fabMenuExpanded) context.getString(R.string.home_floating_action_menu_button_expanded) else context.getString(
                                R.string.home_floating_action_menu_button_collapsed
                            )
                        contentDescription =
                            context.getString(R.string.home_floating_action_menu_button_icon_description)
                    },
                    checked = fabMenuExpanded,
                    onCheckedChange = { fabMenuExpanded = !fabMenuExpanded }
                ) {
                    val imageVector by remember {
                        derivedStateOf {
                            if (checkedProgress > 0.5f) Icons.Filled.Close else Icons.Filled.Add
                        }
                    }
                    Icon(
                        painter = rememberVectorPainter(imageVector),
                        contentDescription = null,
                        modifier = Modifier.animateIcon({ checkedProgress })
                    )
                }
            }
        ) {

            floatingActionButtonMenuItems.forEachIndexed { index, item ->
                FloatingActionButtonMenuItem(
                    modifier = Modifier
                        .semantics {
                            isTraversalGroup = true
                            if (index == floatingActionButtonMenuItems.size - 1) {
                                customActions =
                                    listOf(
                                        CustomAccessibilityAction(
                                            label = context.getString(R.string.home_floating_action_menu_button_icon_description_close),
                                            action = {
                                                fabMenuExpanded = false
                                                true
                                            }
                                        )
                                    )
                            }
                        },
                    onClick = {
                        item.onClick()
                        fabMenuExpanded = false

                    },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.text.asString(),
                            tint = MaterialTheme.colorScheme.onSurface
                        ) },
                    text = {
                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = item.text.asString(),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                )
            }

        }
    }

    LaunchedEffect(
        Unit
    ) {
        appScaffoldStateManager.handleAction(
            MainScaffoldStateAction
                .OnSetFloatingActionButton(floatingActionButton)
        )
    }

}