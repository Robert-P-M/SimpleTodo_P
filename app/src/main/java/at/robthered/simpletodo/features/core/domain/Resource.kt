package at.robthered.simpletodo.features.core.domain

import at.robthered.simpletodo.features.core.presentation.UiText
import at.robthered.simpletodo.features.core.domain.status.Status

sealed interface Resource<out T> {
    data object Stale: Resource<Nothing>
    data class Loading(val loadingInfo: Status? = null) : Resource<Nothing>
    data class Success<T>(val data: T, val info: UiText? = null) : Resource<T>
    data class Error(val error: UiText) : Resource<Nothing>
}