package at.robthered.simpletodo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import at.robthered.simpletodo.features.addTaskDialog.presentation.AddTaskDialogScreenRoot
import at.robthered.simpletodo.features.core.presentation.navigation.MainRoute
import at.robthered.simpletodo.features.core.presentation.theme.AppTheme
import at.robthered.simpletodo.features.dueDialog.presentation.DueDialogScreenRoot
import at.robthered.simpletodo.features.priorityDialogScreen.presentation.PriorityPickerDialogScreenRoot
import at.robthered.simpletodo.features.sharedUrlProcessorDialogScreen.presentation.SharedUrlProcessorDialogScreenRoot
import org.koin.compose.KoinContext

fun Intent.isIntentSharedUrl(): String? {
    if (action == Intent.ACTION_SEND && type == "text/plain") {
        val sharedUrl = getStringExtra(Intent.EXTRA_TEXT)
        if (sharedUrl != null && (sharedUrl.startsWith("https://") || sharedUrl.startsWith("http://"))) {
            return sharedUrl
        }
    }
    return null
}

class ShareUrlReceiverActivity: ComponentActivity() {

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KoinContext {

                AppTheme {
                    val navController = rememberNavController()
                    val startDestination = MainRoute.AddTaskDialog(
                        projectId = null,
                        taskOfSectionId = null,
                        parentTaskId = null,
                        priority = null,
                    )
                    val currentIntent = rememberUpdatedState(newValue = intent)
                    NavHost(
                        navController = navController,
                        startDestination = startDestination
                    ) {
                        composable<MainRoute.AddTaskDialog> {
                            AddTaskDialogScreenRoot(
                                navigateTo = { route ->
                                    navController.navigate(route)
                                },
                                onNavigateBack = {finish()},
                            )
                        }
                        dialog<MainRoute.DueDialog> {
                            DueDialogScreenRoot(
                                onNavigateBack = navController::navigateUp
                            )
                        }
                        dialog<MainRoute.SharedUrlProcessorDialog> {
                            SharedUrlProcessorDialogScreenRoot(
                                onNavigateBack = navController::navigateUp
                            )
                        }
                        dialog<MainRoute.PriorityPickerDialog> {
                            PriorityPickerDialogScreenRoot(
                                onNavigateBack = navController::navigateUp
                            )
                        }
                    }
                    LaunchedEffect(currentIntent.value) {
                        currentIntent.value?.isIntentSharedUrl()?.let { sharedUrl ->
                            navController.navigate(MainRoute.SharedUrlProcessorDialog(url = sharedUrl))
                        }
                    }
                }
            }
        }
    }
}