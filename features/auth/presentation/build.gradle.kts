plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.library.compose)
    alias(libs.plugins.astracrypt.android.hilt)
    alias(libs.plugins.astracrypt.android.hilt.compose)
    alias(libs.plugins.astracrypt.kotlin.coroutines)
}

android {
    namespace = "com.nevidimka655.astracrypt.auth.login"
}

dependencies {
    implementation(projects.features.auth.domain)
    implementation(projects.features.auth.di)
    
    implementation(projects.core.resources)
}