plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.plugin.serialization)
    alias(libs.plugins.jetbrains.kotlin.plugin.compose)
    alias(libs.plugins.androidx.room)
    alias(libs.plugins.google.devtools.ksp)
}

ksp {
    arg("ksp.verbose", "true")
}

room {
    schemaDirectory("$projectDir/schemas")
}

android {
    namespace = "at.robthered.simpletodo"
    compileSdk = 35

    defaultConfig {
        applicationId = "at.robthered.simpletodo"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs += "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
    }
    buildFeatures {
        compose = true
    }

    androidResources {
        generateLocaleConfig = true
    }
}


dependencies {

    // tracing
    implementation(libs.androidx.tracing)
    implementation(libs.androidx.tracing.perfetto)

    // AndroidX Core
    implementation(libs.androidx.core.ktx)

    // Lifecycle & Activity
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.activity.compose)

    // Paging
    implementation(libs.androidx.paging.paging.runtime)
    implementation(libs.androidx.paging.paging.compose)

    // Kotlinx-DateTime
    implementation(libs.jetbrains.kotlinx.kotlinx.datetime)

    // Jetpack Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.ui.graphics)
    implementation(libs.androidx.compose.ui.ui.tooling.preview)
    implementation(libs.androidx.compose.material3.material3)
    implementation(libs.androidx.compose.ui.ui.text.google.fonts)
    testImplementation(libs.junit.jupiter)


    // Debugging
    debugImplementation(libs.androidx.compose.ui.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.ui.test.manifest)

    // Testing
    testImplementation(libs.bundles.local.tests)
    testImplementation(libs.junit)
    testImplementation(libs.androidx.test.core)

    // Instrumented Tests
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.compose.ui.ui.test.junit4)
    androidTestImplementation(libs.androidx.test.espresso.espresso.core)
    testImplementation(libs.androidx.test.runner)

    androidTestImplementation(libs.androidx.test.core.ktx)
    androidTestImplementation(libs.androidx.test.core)

    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.ext.junit.ktx)

    androidTestImplementation(libs.androidx.test.ext.junit.ktx)
    androidTestImplementation(libs.androidx.test.ext.junit)
    testImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.ext.truth)
    androidTestImplementation(libs.androidx.test.orchestrator)
    androidTestImplementation(libs.jetbrains.kotlinx.kotlinx.coroutines.test)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.koin.koin.test)
    androidTestImplementation(libs.koin.koin.test.junit4)
    testImplementation(libs.koin.koin.test.junit4)

    // ktor client
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.auth)

    // coil
    implementation(libs.coil.kt.coil.compose)
    implementation(libs.coil.kt.coil.network.ktor3)

    // apache for html parsing
    implementation(libs.apache.commons.commons.text)



    // Compose Foundation (anchorDraggable)
    implementation(libs.androidx.compose.foundation.foundation)

    // Extended Material Icons
    implementation(libs.androidx.compose.material.material.icons.core)
    implementation(libs.androidx.compose.material.material.icons.extended)

    // Navigation
    implementation(libs.androidx.navigation.navigation.compose)

    // Type-Safe Navigation with Kotlinx Serialization
    implementation(libs.jetbrains.kotlinx.serialization.json)

    // Room Database
    implementation(libs.androidx.room.room.runtime)
    implementation(libs.androidx.room.room.ktx)
    implementation(libs.androidx.room.room.paging)
    testImplementation(libs.androidx.room.room.testing)
    ksp(libs.androidx.room.room.compiler)


    // Koin Dependency Injection
    implementation(libs.bundles.koin)

    // Mockito
    testImplementation(libs.mockito.kotlin)

    // accompanist
    implementation(libs.google.accompanist.accompanist.permissions)

}