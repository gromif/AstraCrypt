plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.library.compose)
    alias(libs.plugins.astracrypt.kotlin.coroutines)
}

android {
    namespace = "com.nevidimka655.atracrypt.core.design_system"
}

dependencies {
    implementation(libs.androidx.core.ktx)
}