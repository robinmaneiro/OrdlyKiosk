plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.robinmaneiro.ordly.kiosk.core.network"
    compileSdk = 36

    defaultConfig {
        minSdk = 25
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)

    // Networking
    api(libs.ktor.core)
    implementation(libs.ktor.cio)
    implementation(libs.ktor.contentNegotiation)
    api(libs.ktor.serialization.jackson)
    implementation(libs.ktor.okhttp)

    // Debug
    debugImplementation(libs.chucker.debug)
    releaseImplementation(libs.chucker.release)
}
