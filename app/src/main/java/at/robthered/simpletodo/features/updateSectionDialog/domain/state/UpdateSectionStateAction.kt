package at.robthered.simpletodo.features.updateSectionDialog.domain.state

import at.robthered.simpletodo.features.dataSource.domain.local.model.SectionModel

sealed interface UpdateSectionStateAction {
    data class OnChangeTitle(val title: String) : UpdateSectionStateAction
    data object OnClearSection : UpdateSectionStateAction
    data object OnUpdateSection : UpdateSectionStateAction
    data class OnInitializeState(
        val sectionModel: SectionModel,
    ) : UpdateSectionStateAction

    data object OnDismiss : UpdateSectionStateAction
}