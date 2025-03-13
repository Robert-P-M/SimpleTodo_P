package at.robthered.simpletodo.features.homeScreen.presentation.components.remember

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import at.robthered.simpletodo.features.core.presentation.navigation.MainRoute

@Composable
fun rememberHomeMenuState(
    onOpenLocalePicker: () -> Unit,
    onNavigateToAddSectionDialog: (args: MainRoute.AddSectionDialog) -> Unit,
): HomeMenuState {
    return remember {
        HomeMenuState(
            onOpenLocalePicker = onOpenLocalePicker,
            onNavigateToAddSectionDialog = onNavigateToAddSectionDialog,
        )
    }
}