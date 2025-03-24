plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.library.compose)
    alias(libs.plugins.astracrypt.android.hilt)
    alias(libs.plugins.astracrypt.android.hilt.compose)
    alias(libs.plugins.astracrypt.kotlin.coroutines)
    alias(libs.plugins.astracrypt.android.work)

    alias(libs.plugins.astracrypt.test.unit)
}

android {
    namespace = "io.gromif.lab_zip.presentation"
}

dependencies {
    implementation(projects.features.labZip.domain)
    implementation(projects.features.labZip.di)

    implementation(libs.androidx.documentfile)

    implementation(projects.ui.resources)
    implementation(projects.core.utils)
    implementation(projects.core.crypto.tink)
}