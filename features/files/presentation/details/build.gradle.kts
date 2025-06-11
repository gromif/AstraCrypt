plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.library.compose)
    alias(libs.plugins.astracrypt.android.hilt)
    alias(libs.plugins.astracrypt.android.hilt.compose)
    alias(libs.plugins.astracrypt.android.coil)

    alias(libs.plugins.astracrypt.kotlin.coroutines)
    alias(libs.plugins.astracrypt.test.unit)
}

android {
    namespace = "io.gromif.astracrypt.files.details"
}

dependencies {
    implementation(projects.features.files.domain)
    implementation(projects.features.files.di)
    implementation(projects.features.files.presentation.shared)

    implementation(projects.ui.resources)
    implementation(projects.ui.composeDetails)

    implementation(projects.core.crypto.tink)
}
