package at.robthered.simpletodo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import at.robthered.simpletodo.features.alarm.data.AppNotificationManagerImpl
import at.robthered.simpletodo.features.core.presentation.components.AppScaffold
import at.robthered.simpletodo.features.core.presentation.navigation.MainActivityNavigation
import at.robthered.simpletodo.features.core.presentation.state.scaffoldState.rememberAppScaffoldStateManager
import at.robthered.simpletodo.features.core.presentation.theme.AppTheme
import org.koin.compose.KoinContext

fun Intent.isIncomingNotificationIntent(): Long? {
    val result = this.getLongExtra(AppNotificationManagerImpl.TASK_ID_KEY, -1)
    return if(result == -1L) {
        null
    } else result
}

class MainActivity : ComponentActivity() {

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()

        enableEdgeToEdge()
        setContent {
            KoinContext {

                AppTheme {


                    val taskId = intent.getLongExtra(AppNotificationManagerImpl.TASK_ID_KEY, -1)

                    val appScaffoldStateManager = rememberAppScaffoldStateManager()

                    val navController = rememberNavController()


                    AppScaffold(
                        scaffoldState = appScaffoldStateManager,
                    ) { paddingValues, snackbarHostState ->
                        MainActivityNavigation(
                            navController = navController,
                            intentTaskId = if(taskId != -1L) taskId else null,
                            mainScaffoldStateManager = appScaffoldStateManager,
                            snackbarHostState = snackbarHostState,
                            paddingValues = paddingValues,
                        )
                    }

                }
            }
        }
    }


    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            "task_reminder_channel",
            "Task Reminder",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Channel for task reminder notifications"
        }

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

}