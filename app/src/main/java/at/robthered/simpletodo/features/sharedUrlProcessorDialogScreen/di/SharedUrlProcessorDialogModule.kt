package at.robthered.simpletodo.features.sharedUrlProcessorDialogScreen.di

import androidx.lifecycle.SavedStateHandle
import at.robthered.simpletodo.features.core.domain.eventBus.events.SharedUrlModelEventBus
import at.robthered.simpletodo.features.core.domain.use_case.validation.ValidateLinkUrlUseCase
import at.robthered.simpletodo.features.network.domain.use_case.GetSharedUrlDataUseCase
import at.robthered.simpletodo.features.core.data.use_case.validation.ValidateLinkDescriptionUseCaseImpl
import at.robthered.simpletodo.features.core.data.use_case.validation.ValidateLinkTitleUseCaseImpl
import at.robthered.simpletodo.features.core.data.use_case.validation.ValidateLinkUrlUseCaseImpl
import at.robthered.simpletodo.features.core.domain.use_case.validation.ValidateLinkDescriptionUseCase
import at.robthered.simpletodo.features.core.domain.use_case.validation.ValidateLinkTitleUseCase
import at.robthered.simpletodo.features.sharedUrlProcessorDialogScreen.presentation.SharedUrlProcessorDialogViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val sharedUrlProcessorDialogModule = module {

    single<ValidateLinkUrlUseCase> { ValidateLinkUrlUseCaseImpl() }
    single<ValidateLinkTitleUseCase> { ValidateLinkTitleUseCaseImpl() }
    single<ValidateLinkDescriptionUseCase> { ValidateLinkDescriptionUseCaseImpl() }

    viewModel {
        SharedUrlProcessorDialogViewModel(
            savedStateHandle = get<SavedStateHandle>(),
            getSharedUrlDataUseCase = get<GetSharedUrlDataUseCase>(),
            validateLinkUrlUseCase = get<ValidateLinkUrlUseCase>(),
            validateLinkTitleUseCase = get<ValidateLinkTitleUseCase>(),
            validateLinkDescriptionUseCase = get<ValidateLinkDescriptionUseCase>(),
            shareUrlModelEventBus = get<SharedUrlModelEventBus>()
        )
    }


}