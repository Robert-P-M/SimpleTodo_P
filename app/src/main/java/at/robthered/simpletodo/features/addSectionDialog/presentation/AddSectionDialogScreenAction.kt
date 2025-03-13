package at.robthered.simpletodo.features.addSectionDialog.presentation

import at.robthered.simpletodo.features.core.domain.state_manager.section.SectionStateAction

sealed interface AddSectionDialogScreenAction {
    data object OnNavigateBack: AddSectionDialogScreenAction
    data object OnSaveSection: AddSectionDialogScreenAction
    data class SectionAction(val action: SectionStateAction): AddSectionDialogScreenAction
}