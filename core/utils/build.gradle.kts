plugins {
    alias(libs.plugins.astracrypt.android.library)
}

android {
    namespace = "com.nevidimka655.astracrypt.utils"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(libs.androidx.annotation.jvm)
    implementation(libs.androidx.core.ktx)
}