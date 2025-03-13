package at.robthered.simpletodo.features.core.presentation.ext

import at.robthered.simpletodo.features.core.domain.Resource
import at.robthered.simpletodo.features.core.presentation.UiText
import io.ktor.util.reflect.instanceOf

fun Resource<*>.loadingInfo(): UiText? {
    return (this as? Resource.Loading)?.loadingInfo?.toUiText()
}

fun Resource<*>.isSaving(): Boolean {
    return this is Resource.Loading
}

fun Resource<*>.isSuccess(): Boolean {
    return this is Resource.Success
}

fun <T>Resource<T?>.getData(): T? {
    return (this as? Resource.Success<T?>)?.data
}