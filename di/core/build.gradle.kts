plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.hilt)
    alias(libs.plugins.astracrypt.kotlin.coroutines)
}

android {
    namespace = "com.nevidimka655.astracrypt.core.di"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}