package at.robthered.simpletodo.features.core.presentation.navigation

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import at.robthered.simpletodo.features.addSectionDialog.presentation.AddSectionDialogScreenRoot
import at.robthered.simpletodo.features.addTaskDialog.presentation.AddTaskDialogScreenRoot
import at.robthered.simpletodo.features.core.domain.state.scaffoldState.MainScaffoldStateManager
import at.robthered.simpletodo.features.dueDialog.presentation.DueDialogScreenRoot
import at.robthered.simpletodo.features.homeScreen.presentation.HomeScreenRoot
import at.robthered.simpletodo.features.priorityDialogScreen.presentation.PriorityPickerDialogScreenRoot
import at.robthered.simpletodo.features.sharedUrlProcessorDialogScreen.presentation.SharedUrlProcessorDialogScreenRoot
import at.robthered.simpletodo.features.taskActivityProtocolDialog.presentation.TaskActivityDialogRoot
import at.robthered.simpletodo.features.taskDetailsDialog.presentation.TaskDetailsDialogRoot
import at.robthered.simpletodo.features.updatePriorityDialog.presentation.UpdatePriorityDialogRoot
import at.robthered.simpletodo.features.updateSectionDialog.presentation.UpdateSectionDialogRoot
import at.robthered.simpletodo.features.updateTaskDialog.presentation.UpdateTaskDialogRoot

@Composable
fun MainActivityNavigation(
    mainScaffoldStateManager: MainScaffoldStateManager,
    navController: NavHostController,
    paddingValues: PaddingValues,
    snackbarHostState: SnackbarHostState,
    intentTaskId: Long?,
) {

    LaunchedEffect(intentTaskId) {
        if(intentTaskId != null) {
            navController.navigate(
                MainRoute.TaskDetailsDialog(
                    taskId = intentTaskId
                )
            )
        }
    }

    NavHost(
        modifier = Modifier.padding(paddingValues = paddingValues),
        navController = navController,
        startDestination = MainRoute.HomeScreen
    ) {

        val navigateTo: (route: MainRoute) -> Unit = { route ->
            navController.navigate(route)

        }
        val onNavigateBack: () -> Unit = {
            navController.navigateUp()
        }




        composable<MainRoute.HomeScreen> {
            HomeScreenRoot(
                modifier = Modifier,
                navigateTo = navigateTo,
                appScaffoldStateManager = mainScaffoldStateManager,
            )
        }


        dialog<MainRoute.TaskDetailsDialog> {
            TaskDetailsDialogRoot(
                modifier = Modifier,
                navigateTo = navigateTo,
                onNavigateBack = onNavigateBack,
            )
        }
        dialog<MainRoute.AddTaskDialog> {
            AddTaskDialogScreenRoot(
                modifier = Modifier,
                navigateTo = navigateTo,
                onNavigateBack = onNavigateBack,
            )
        }
        dialog<MainRoute.UpdateTaskDialog> {
            UpdateTaskDialogRoot(
                modifier = Modifier,
                navigateTo = navigateTo,
                onNavigateBack = onNavigateBack,
            )
        }
        dialog<MainRoute.AddSectionDialog> {
            AddSectionDialogScreenRoot(
                modifier = Modifier,
                onNavigateBack = onNavigateBack,
            )
        }
        dialog<MainRoute.UpdateSectionDialog> {
            UpdateSectionDialogRoot(
                modifier = Modifier,
                onNavigateBack = onNavigateBack,
            )
        }
        dialog<MainRoute.UpdatePriorityDialog> {
            UpdatePriorityDialogRoot(
                modifier = Modifier,
                onNavigateBack = onNavigateBack,
            )
        }
        dialog<MainRoute.DueDialog> {
            DueDialogScreenRoot(
                modifier = Modifier,
                onNavigateBack = onNavigateBack,
            )
        }
        dialog<MainRoute.TaskActivityLogDialog> {
            TaskActivityDialogRoot(
                modifier = Modifier,
                onNavigateBack = onNavigateBack,
            )
        }
        dialog<MainRoute.SharedUrlProcessorDialog> {
            SharedUrlProcessorDialogScreenRoot(
                onNavigateBack = onNavigateBack
            )
        }
        dialog<MainRoute.PriorityPickerDialog> {
            PriorityPickerDialogScreenRoot(
                onNavigateBack = onNavigateBack
            )
        }
    }
}