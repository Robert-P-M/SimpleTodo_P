package at.robthered.simpletodo.features.core.domain.state_manager.section

import at.robthered.simpletodo.features.dataSource.domain.local.model.SectionModel
import at.robthered.simpletodo.features.homeScreen.presentation.error.SectionModelError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface SectionStateManager {
    val sectionModel: StateFlow<SectionModel>
    val sectionModelError: StateFlow<SectionModelError>
    val canSave: Flow<Boolean>
    fun handleAction(action: SectionStateAction)
}