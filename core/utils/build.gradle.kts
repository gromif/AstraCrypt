plugins {
    alias(libs.plugins.astracrypt.android.library)
}

android {
    namespace = "com.nevidimka655.astracrypt.utils"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}