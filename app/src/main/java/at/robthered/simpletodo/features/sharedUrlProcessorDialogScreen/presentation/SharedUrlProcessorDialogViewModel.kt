package at.robthered.simpletodo.features.sharedUrlProcessorDialogScreen.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import at.robthered.simpletodo.features.core.domain.Resource
import at.robthered.simpletodo.features.core.domain.eventBus.events.SharedUrlModelEvent
import at.robthered.simpletodo.features.core.domain.eventBus.events.SharedUrlModelEventBus
import at.robthered.simpletodo.features.core.domain.use_case.validation.ValidateLinkUrlUseCase
import at.robthered.simpletodo.features.core.presentation.UiText
import at.robthered.simpletodo.features.core.presentation.navigation.MainRoute
import at.robthered.simpletodo.features.core.utils.Result
import at.robthered.simpletodo.features.core.presentation.error.toUiText
import at.robthered.simpletodo.features.network.domain.model.SharedUrlModel
import at.robthered.simpletodo.features.network.domain.use_case.GetSharedUrlDataUseCase
import at.robthered.simpletodo.features.core.domain.use_case.validation.ValidateLinkDescriptionUseCase
import at.robthered.simpletodo.features.core.domain.use_case.validation.ValidateLinkTitleUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface SharedUrlProcessorDialogAction {
    data class OnChangeUrl(val url: String): SharedUrlProcessorDialogAction
    data class OnChangeTitle(val title: String): SharedUrlProcessorDialogAction
    data class OnChangeDescription(val description: String): SharedUrlProcessorDialogAction
    data object OnSave: SharedUrlProcessorDialogAction
}

class SharedUrlProcessorDialogViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val getSharedUrlDataUseCase: GetSharedUrlDataUseCase,
    private val validateLinkUrlUseCase: ValidateLinkUrlUseCase,
    private val validateLinkTitleUseCase: ValidateLinkTitleUseCase,
    private val validateLinkDescriptionUseCase: ValidateLinkDescriptionUseCase,
    private val shareUrlModelEventBus: SharedUrlModelEventBus
    ): ViewModel() {

    private val initialUrl = savedStateHandle.toRoute<MainRoute.SharedUrlProcessorDialog>().url ?: ""
    private val _linkInput: MutableStateFlow<String> = MutableStateFlow(initialUrl)
    val linkInput: StateFlow<String> =
        _linkInput
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = initialUrl
            )

    private val _titleInput: MutableStateFlow<String> = MutableStateFlow("")
    val titleInput: StateFlow<String>
    = _titleInput
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ""
        )

    private val _descriptionInput: MutableStateFlow<String?>  = MutableStateFlow("")
    val descriptionInput: StateFlow<String?>
            = _descriptionInput
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ""
        )

    private val _imageUrl: MutableStateFlow<String?> = MutableStateFlow(null)

    fun handleAction(action: SharedUrlProcessorDialogAction) {
        when (action) {
            is SharedUrlProcessorDialogAction.OnChangeUrl -> {
                _linkInput.update { action.url }
            }
            is SharedUrlProcessorDialogAction.OnChangeTitle -> {
                _titleInput.update { action.title }
            }
            is SharedUrlProcessorDialogAction.OnChangeDescription -> {
                _descriptionInput.update { action.description }
            }
            SharedUrlProcessorDialogAction.OnSave -> {
                val sharedUrlModel = SharedUrlModel(
                    title = titleInput.value,
                    description = descriptionInput.value,
                    link = linkInput.value,
                    imageUrl = _imageUrl.value,
                )
                viewModelScope.launch {
                    shareUrlModelEventBus.publish(
                        event = SharedUrlModelEvent.NewSharedUrlModelEvent(
                            sharedUrlModel = sharedUrlModel
                        )
                    )
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    val linkError = _linkInput
        .debounce(300)
        .map { url ->
            when(val result = validateLinkUrlUseCase(url)) {
                is Result.Error -> result.error.toUiText()
                is Result.Success -> null
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    @OptIn(FlowPreview::class)
    val titleError = _titleInput
        .debounce(300)
        .map { title ->
            when(val result = validateLinkTitleUseCase(title)) {
                is Result.Error -> result.error.toUiText()
                is Result.Success -> null
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    @OptIn(FlowPreview::class)
    val descriptionError = _descriptionInput
        .debounce(300)
        .map { description ->
            when(val result = validateLinkDescriptionUseCase(description)) {
                is Result.Error -> result.error.toUiText()
                is Result.Success -> null
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )


    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val sharedUrlModelResource: StateFlow<Resource<SharedUrlModel>> =
        _linkInput
        .debounce(300)
        .distinctUntilChanged()
            .flatMapLatest { url ->
                getSharedUrlDataUseCase(
                    url = url
                ).map { resource ->
                    if(resource is Resource.Success) {
                        _titleInput.update {
                            resource.data.title
                        }
                        _descriptionInput.update {
                            resource.data.description
                        }
                        _imageUrl.update {
                            resource.data.imageUrl
                        }
                        _linkInput.update {
                            resource.data.link
                        }
                    }
                    resource
                }
                    .catch { emit(Resource.Error(error = UiText.DynamicString(
                        value = it.localizedMessage ?: "Unknown error"
                    ))) }
            }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Resource.Loading()
        )

    val canSave = combine(
        linkError, titleError, descriptionError
    ) { errors ->
        errors.all { it == null }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

}