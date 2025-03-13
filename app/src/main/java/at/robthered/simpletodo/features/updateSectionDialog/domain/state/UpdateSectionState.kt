package at.robthered.simpletodo.features.updateSectionDialog.domain.state

import at.robthered.simpletodo.features.dataSource.domain.local.model.SectionModel
import at.robthered.simpletodo.features.homeScreen.presentation.error.SectionModelError

data class UpdateSectionState(
    val updateSection: SectionModel = SectionModel(),
    val sectionModelErrorState: SectionModelError = SectionModelError(),
    val canSave: Boolean = false,
)