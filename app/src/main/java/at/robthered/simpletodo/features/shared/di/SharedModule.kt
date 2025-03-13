package at.robthered.simpletodo.features.shared.di

import android.content.Context
import android.content.SharedPreferences
import at.robthered.simpletodo.features.dataSource.data.use_case.link.SaveLinkToTaskUseCaseImpl
import at.robthered.simpletodo.features.dataSource.domain.repository.link.LinkRepository
import at.robthered.simpletodo.features.dataSource.domain.use_case.link.SaveLinkToTaskUseCase
import at.robthered.simpletodo.features.shared.date_time.data.DateTimeAppCalendarUtils
import at.robthered.simpletodo.features.shared.date_time.data.KotlinDateTimeRepositoryImpl
import at.robthered.simpletodo.features.shared.date_time.domain.AppCalendarUtils
import at.robthered.simpletodo.features.shared.date_time.domain.AppDateTimeRepository
import at.robthered.simpletodo.features.shared.image.data.repository.ImageStorageRepositoryImpl
import at.robthered.simpletodo.features.shared.image.data.use_case.SaveImageUseCaseImpl
import at.robthered.simpletodo.features.shared.image.data.use_case.TransformSharedUrlToLinkModelUseCaseImpl
import at.robthered.simpletodo.features.shared.image.domain.repository.ImageStorageRepository
import at.robthered.simpletodo.features.shared.image.domain.use_case.SaveImageUseCase
import at.robthered.simpletodo.features.shared.image.domain.use_case.TransformSharedUrlToLinkModelUseCase
import at.robthered.simpletodo.features.shared.locale.data.AppLocaleManagerImpl
import at.robthered.simpletodo.features.shared.locale.data.AppLocaleProviderImpl
import at.robthered.simpletodo.features.shared.locale.data.SharedPreferencesLocaleStorage
import at.robthered.simpletodo.features.shared.locale.domain.AppLocaleManager
import at.robthered.simpletodo.features.shared.locale.domain.AppLocaleProvider
import at.robthered.simpletodo.features.shared.locale.domain.LocaleStorage
import at.robthered.simpletodo.features.shared.locale.domain.state.AppLocaleStateManager
import at.robthered.simpletodo.features.shared.locale.presentation.state.AppLocaleStateManagerImpl
import at.robthered.simpletodo.features.shared.shareUrls.domain.state.ShareUrlsStateManager
import at.robthered.simpletodo.features.shared.shareUrls.presentation.state.ShareUrlsStateManagerImpl
import at.robthered.simpletodo.features.shared.time_zone.data.SharedPreferencesTimeZoneStorage
import at.robthered.simpletodo.features.shared.time_zone.data.SystemAppTimeZoneProvider
import at.robthered.simpletodo.features.shared.time_zone.domain.AppTimeZoneProvider
import at.robthered.simpletodo.features.shared.time_zone.domain.TimeZoneStorage
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val sharedModule = module {


    single<AppLocaleManager> {
        AppLocaleManagerImpl(
            context = androidContext()
        )
    }


    single<LocaleStorage> {
        SharedPreferencesLocaleStorage(
            sharedPreferences = get<SharedPreferences>()
        )
    }

    single<AppLocaleProvider> {
        AppLocaleProviderImpl(
            localeStorage = get<LocaleStorage>(),
            appLocaleManager = get<AppLocaleManager>(),
        )
    }

    single<TimeZoneStorage> {
        SharedPreferencesTimeZoneStorage(
            sharedPreferences = get<SharedPreferences>()
        )
    }

    single<AppTimeZoneProvider> {
        SystemAppTimeZoneProvider(
            timeZoneStorage = get<TimeZoneStorage>()
        )
    }

    single<AppDateTimeRepository> {
        KotlinDateTimeRepositoryImpl(
            localeProvider = get<AppLocaleProvider>(),
            timeZoneProvider = get<AppTimeZoneProvider>()
        )
    }

    single<AppCalendarUtils> {
        DateTimeAppCalendarUtils(
            localeProvider = get<AppLocaleProvider>(),
            appDateTimeRepository = get<AppDateTimeRepository>(),
        )
    }

    factory<AppLocaleStateManager> {
        AppLocaleStateManagerImpl(
            coroutineScope = get<CoroutineScope>(),
            localeProvider = get<AppLocaleProvider>()
        )
    }

    single<ImageStorageRepository> {
        ImageStorageRepositoryImpl(
            context = androidContext(),
            httpClient = get<HttpClient>(),

        )
    }
    single<ShareUrlsStateManager> {
        ShareUrlsStateManagerImpl(
            coroutineScope = get<CoroutineScope>(),
        )
    }

    single<SaveImageUseCase> {
        SaveImageUseCaseImpl(
            imageStorageRepository = get<ImageStorageRepository>()
        )
    }

    single<TransformSharedUrlToLinkModelUseCase> {
        TransformSharedUrlToLinkModelUseCaseImpl(
            imageStorageRepository = get<ImageStorageRepository>()
        )
    }

    single<SaveLinkToTaskUseCase> {
        SaveLinkToTaskUseCaseImpl(
            linkRepository = get<LinkRepository>(),
            transformSharedUrlToLinkModelUseCase = get<TransformSharedUrlToLinkModelUseCase>()
        )
    }
}