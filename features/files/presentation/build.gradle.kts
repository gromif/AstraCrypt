plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.library.compose)
    alias(libs.plugins.astracrypt.android.hilt)
    alias(libs.plugins.astracrypt.android.hilt.compose)
    alias(libs.plugins.astracrypt.kotlin.coroutines)
    alias(libs.plugins.astracrypt.kotlin.parcelize)
    alias(libs.plugins.astracrypt.android.paging)
    alias(libs.plugins.astracrypt.android.work)
    alias(libs.plugins.astracrypt.android.coil)
}

android {
    namespace = "io.gromif.astracrypt.files"
}

dependencies {
    implementation(projects.features.files.domain)
    implementation(projects.features.files.di)

    implementation(projects.core.designSystem)
    implementation(projects.core.resources)
    implementation(projects.core.utils)
}