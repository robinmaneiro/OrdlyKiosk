plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.detekt)
    alias(libs.plugins.stability.analyzer)
}

android {
    namespace = "com.robinmaneiro.orderkiosk"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.robinmaneiro.orderkiosk"
        minSdk = 25
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "SERVER_BASE_URL", "\"http://192.168.1.162:8080\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String", "SERVER_BASE_URL", "\"https://api.yourapp.com\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
        compilerOptions.freeCompilerArgs = listOf("-XXLanguage:+PropertyParamAnnotationDefaultTargetMode")
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //region AndroidX/KotlinX
    implementation(libs.datastore)
    implementation(libs.immutable.collections)
    //endregion

    //region Third party
    implementation(libs.lottie.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.coil.compose)
    implementation(libs.zxing)
    //endregion

    //region Dependency injection
    implementation(libs.koin.compose)
    //endregion

    //region Fonts
    implementation(libs.ui.text.google.fonts)
    //endregion

    //region Networking
    implementation(libs.ktor.core)
    implementation(libs.ktor.cio)
    implementation(libs.ktor.contentNegotiation)
    implementation(libs.ktor.serialization.jackson)
    implementation(libs.ktor.okhttp)
    //endregion

    //region Crypto
    implementation(libs.tink)
    //endregion

    //region Debug
    debugImplementation(libs.chucker.debug)
    releaseImplementation(libs.chucker.release)
    implementation(libs.timber)
    //endregion

    //region Detekt Plugins
    detektPlugins(libs.detekt.compose.rules)
    detektPlugins(libs.detekt.rules.formatting)
    detektPlugins(libs.detekt.rules.ktlint)
    detektPlugins(libs.detekt.rules.libraries)
    detektPlugins(libs.detekt.rules.ruleauthors)
    //endregion
}

detekt {
    autoCorrect = true
    config.setFrom("../detekt/detekt-config.yml")
    baseline = file("../detekt/detekt-baseline.yml")
}
