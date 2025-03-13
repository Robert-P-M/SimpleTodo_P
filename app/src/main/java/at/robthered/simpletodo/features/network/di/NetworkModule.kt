package at.robthered.simpletodo.features.network.di

import at.robthered.simpletodo.features.core.domain.use_case.validation.ValidateLinkUrlUseCase
import at.robthered.simpletodo.features.network.data.repository.ShareUrlRepositoryImpl
import at.robthered.simpletodo.features.network.data.use_case.GetSharedUrlDataUseCaseImpl
import at.robthered.simpletodo.features.network.domain.repository.ShareUrlRepository
import at.robthered.simpletodo.features.network.domain.use_case.GetSharedUrlDataUseCase
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single<HttpClient> {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
    }

    single<ShareUrlRepository> {
        ShareUrlRepositoryImpl(
            client = get<HttpClient>()
        )
    }

    single<GetSharedUrlDataUseCase> {
        GetSharedUrlDataUseCaseImpl(
            shareUrlRepository = get<ShareUrlRepository>(),
            validateSharedUrlUseCase = get<ValidateLinkUrlUseCase>(),
        )
    }
}