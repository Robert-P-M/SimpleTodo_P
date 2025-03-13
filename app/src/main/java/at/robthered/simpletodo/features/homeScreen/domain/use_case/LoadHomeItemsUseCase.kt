package at.robthered.simpletodo.features.homeScreen.domain.use_case

import at.robthered.simpletodo.features.core.domain.Resource
import at.robthered.simpletodo.features.homeScreen.domain.model.ScreenItemsModel
import kotlinx.coroutines.flow.Flow

interface LoadHomeItemsUseCase {
    operator fun invoke(depth: Int = 5): Flow<Resource<List<ScreenItemsModel>>>
}