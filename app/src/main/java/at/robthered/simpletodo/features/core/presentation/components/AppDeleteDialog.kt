package at.robthered.simpletodo.features.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import at.robthered.simpletodo.features.homeScreen.presentation.components.remember.DeleteDialogState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDeleteDialog(
    modifier: Modifier = Modifier,
    deleteDialogState: DeleteDialogState,
    text: @Composable () -> Unit,
) {


    BasicAlertDialog(
        modifier = modifier,
        onDismissRequest = {
            deleteDialogState.onHide()
        },
        properties = DialogProperties(
            dismissOnClickOutside = true,
        ),

        ) {
        Card(
            shape = MaterialTheme.shapes.large,
            border = BorderStroke(
                1.dp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.05f)
            ),
        ) {
            Column(
                modifier = Modifier.padding(16.dp, 8.dp),
            ) {
                text()
                Spacer(modifier = Modifier.padding(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = {
                            deleteDialogState.onHide()
                        },
                        colors = ButtonDefaults.outlinedButtonColors()
                    ) {
                        Text(
                            text = "Cancel"
                        )
                    }
                    OutlinedButton(
                        onClick = {
                            deleteDialogState.onDeleteClick()
                        },
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.1f),
                            disabledContainerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.3f)
                        ),
                    ) {
                        Text(
                            text = "Delete",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}