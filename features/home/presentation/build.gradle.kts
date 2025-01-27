plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.library.compose)
    alias(libs.plugins.astracrypt.android.hilt)
    alias(libs.plugins.astracrypt.android.hilt.compose)
    alias(libs.plugins.astracrypt.kotlin.coroutines)
    alias(libs.plugins.astracrypt.kotlin.parcelize)
    alias(libs.plugins.astracrypt.android.coil)
}

android {
    namespace = "io.gromif.astracrypt.home.presentation"
}

dependencies {
    implementation(projects.features.home.domain)
    implementation(projects.features.home.di)

    implementation(projects.core.designSystem)
    implementation(projects.core.resources)
    implementation(projects.core.utils)
}