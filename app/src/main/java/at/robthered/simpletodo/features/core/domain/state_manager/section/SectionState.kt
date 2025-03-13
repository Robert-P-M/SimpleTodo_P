package at.robthered.simpletodo.features.core.domain.state_manager.section

import at.robthered.simpletodo.features.dataSource.domain.local.model.SectionModel
import at.robthered.simpletodo.features.homeScreen.presentation.error.SectionModelError

data class SectionState(
    val section: SectionModel = SectionModel(),
    val sectionModelErrorState: SectionModelError = SectionModelError(),
    val canSave: Boolean = false,
)