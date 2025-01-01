plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.library.compose)
    alias(libs.plugins.astracrypt.kotlin.coroutines)
}

android {
    namespace = "com.nevidimka655.astracrypt.auth.login"
}

dependencies {
    implementation(projects.domain.auth)
    implementation(projects.di.auth)
}