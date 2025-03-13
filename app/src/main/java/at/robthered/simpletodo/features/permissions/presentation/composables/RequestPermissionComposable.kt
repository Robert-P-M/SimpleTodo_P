package at.robthered.simpletodo.features.permissions.presentation.composables

import android.Manifest
import android.os.Build
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import at.robthered.simpletodo.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermissionComposable(
    permission: String,
    rationaleMessage: String,
    onPermissionResult: (isGranted: Boolean) -> Unit,
) {
    val permissionState = rememberPermissionState(permission)

    LaunchedEffect(
        permissionState
    ) {
        if(permissionState.status.isGranted) {
            onPermissionResult(true)
        }
    }

    when {
        permissionState.status.shouldShowRationale -> {
            RationaleDialog(
                rationaleMessage = rationaleMessage,
                onRequestPermission = {
                    permissionState.launchPermissionRequest()
                }
            )
        }
        else -> {
            LaunchedEffect(Unit) {
                permissionState.launchPermissionRequest()
            }
        }
    }
}

@Composable
fun RationaleDialog(
    rationaleMessage: String,
    onRequestPermission: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text(stringResource(R.string.permission_request_required_message)) },
        text = { Text(text = rationaleMessage) },
        confirmButton = {
            TextButton(onClick = onRequestPermission) {
                Text(stringResource(R.string.permission_request_grant_permission))
            }
        }
    )
}

@Composable
fun RequestPostNotificationPermission(snackbarHostState: SnackbarHostState) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val context = LocalContext.current
        val scope = rememberCoroutineScope()
        RequestPermissionComposable(
            permission = Manifest.permission.POST_NOTIFICATIONS,
            rationaleMessage = stringResource(id = R.string.permission_request_post_notification),
            onPermissionResult = { isGranted ->
                if(isGranted) {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = context.getString(R.string.permission_request_post_notification_granted),
                            withDismissAction = true,
                            duration = SnackbarDuration.Long,
                        )
                    }
                } else {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = context.getString(R.string.permission_request_post_notification_denied),
                            withDismissAction = true,
                            duration = SnackbarDuration.Long,
                        )
                    }
                }
            }
        )
    }
}