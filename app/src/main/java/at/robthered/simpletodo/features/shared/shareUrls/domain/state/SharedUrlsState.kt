package at.robthered.simpletodo.features.shared.shareUrls.domain.state

import at.robthered.simpletodo.features.dataSource.domain.local.model.LinkModel
import at.robthered.simpletodo.features.network.domain.model.SharedUrlModel

data class SharedUrlsState(
    val sharedUrlModels: List<SharedUrlModel> = emptyList(),
    val isLoading: Boolean = false,
)