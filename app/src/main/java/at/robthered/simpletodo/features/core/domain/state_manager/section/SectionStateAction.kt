package at.robthered.simpletodo.features.core.domain.state_manager.section

import at.robthered.simpletodo.features.dataSource.domain.local.model.SectionModel

sealed interface SectionStateAction {
    data class OnChangeTitle(val title: String) : SectionStateAction
    data object OnClearSection : SectionStateAction
    data class OnInitializeState(
        val sectionModel: SectionModel,
    ) : SectionStateAction

    data object OnDismiss : SectionStateAction
}