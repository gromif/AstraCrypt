plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.library.compose)
    alias(libs.plugins.astracrypt.kotlin.coroutines)
    alias(libs.plugins.astracrypt.android.hilt)
    alias(libs.plugins.astracrypt.android.hilt.compose)
    alias(libs.plugins.astracrypt.android.paging)
    alias(libs.plugins.astracrypt.android.work)

    alias(libs.plugins.astracrypt.test.unit)
}

android {
    namespace = "io.gromif.notes.presentation"
}

dependencies {
    implementation(projects.features.notes.domain)
    implementation(projects.features.notes.di)

    implementation(projects.core.utils)
    implementation(projects.core.crypto.tink)
    implementation(projects.ui.resources)
}